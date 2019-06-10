package com.isoft.system.advice;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.entity.SysLogs;
import com.isoft.system.service.ISysLogService;
import com.isoft.system.utils.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.isoft.system.utils.JwtHelper;
import com.isoft.system.utils.Util;

import io.swagger.annotations.ApiOperation;

/**
 * 统一日志处理
 * 
 */
@Aspect
@Component
public class LogAdvice {

	@Autowired
	private ISysLogService logService;

	private final Logger logger = LoggerFactory.getLogger(LogAdvice.class);

	private static String AUTH_HEADER = "Authorization";
	private static String TOKEN_HEADER = "token";

	@Around(value = "@annotation(com.isoft.system.annotation.LogAnnotation)")
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
		SysLogs sysLogs = new SysLogs();
		int userIdInt = 0;
		try {
			Object object = joinPoint.proceed();

			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = sra.getRequest();

			String module = null;
			LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
			module = logAnnotation.module();
			if (StringUtils.isEmpty(module)) {
				ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
				if (apiOperation != null) {
					module = apiOperation.value();
				}
			}

			
			//请求地址
			String url = request.getRequestURL().toString();
			if(!StringUtils.isEmpty(url)) {
				sysLogs.setUrl(url);
			}
			//parameter参数
			String paramKeySet = "";
			Enumeration em = request.getParameterNames();
			 while (em.hasMoreElements()) {
			    String name = (String) em.nextElement();
			    String value = request.getParameter(name);
			    paramKeySet += "param: "+name+"  value: "+value;
			}
			 
			if(!StringUtils.isEmpty(paramKeySet)) {
				sysLogs.setRemark(paramKeySet);
			}
//			if(null!=requestArgu && !requestArgu.isEmpty()) {
//				sysLogs.setRemark(requestArgu);
//			}
			//body参数
//			String str = request.getQueryString();
			BufferedReader bufferedReader = request.getReader();
			String bodyStr = IOUtils.read(bufferedReader);

			if(!StringUtils.isEmpty(bodyStr)) {
				sysLogs.setRequestBody(bodyStr);
			}

			if (StringUtils.isEmpty(module)) {
				throw new RuntimeException("没有指定日志module");
			}
			String token = "";
			String userId = "";
//			int userIdInt = 0;
			if (module.contains("login")) {
				// 第一次登陆不带token
				if (module.contains("login-no-token")) {
					return object;
				}
				token = request.getParameter("token");
				logger.info("token: " + token);
				userId = JwtHelper.getUserId(token);
				logger.info(" userId: " + userId);
				userIdInt = Integer.parseInt(userId);
			} else {
//			  String authorization = request.getHeader(AUTH_HEADER);
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("token")) {
						token = cookie.getValue();

					}
				}

//			  logger.info(" authorization: " + authorization);
//			  token = authorization.replace("Bearer ", "");
				logger.info(" token from authorization: " + token);
				userId = JwtHelper.getUserId(token);
				userIdInt = Integer.parseInt(userId);
				logger.info(" userId: " + userId);

			}
			sysLogs.setUserId(userIdInt);
			sysLogs.setModule(module);
			sysLogs.setCreateTime(Util.createTimestamp());

			sysLogs.setFlag(true);
			logService.save(sysLogs);

			return object;
		} catch (Exception e) {
			sysLogs.setFlag(false);
			sysLogs.setUserId(userIdInt);
			sysLogs.setCreateTime(Util.createTimestamp());
			sysLogs.setRemark(e.getMessage());
			logService.save(sysLogs);
			throw e;
		}

	}
}
