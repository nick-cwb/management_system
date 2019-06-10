package com.isoft.system.controller;

import com.isoft.cache.tools.RedisCacheOperator;
import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.User;
import com.isoft.system.enums.ExceptionEnum;
import com.isoft.system.enums.ResultEnum;
import com.isoft.system.service.ILoginService;

import io.netty.util.internal.StringUtil;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController extends BaseController {

    @Resource
    ILoginService service;

    @LogAnnotation
    @ApiOperation("登录用户-login-no-token")
    @PostMapping(value = "/login",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response, @RequestBody User user){
        Object object = service.signToken(user);
        return renderSuccess(object);
    }

    @LogAnnotation
    @ApiOperation("登录用户token-login")
    @GetMapping(value = "/login",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUserByToken(HttpServletRequest request, HttpServletResponse response,String token){
        User user = service.findUserByToken(token);
        if(user == null){
            renderError(ResultEnum.NONE_RESULT.value());
        }
        return renderSuccess(user);
    }

    @RequiresAuthentication
    @GetMapping(value = "/redis",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object redisTest(HttpServletRequest request, HttpServletResponse response){
        RedisCacheOperator resOp = new RedisCacheOperator(redisTemplate);
            String key = "redisKey";
            String value = "123";
            // redis set value
            String str = (String) resOp.get(key);
            if(!StringUtil.isNullOrEmpty(str)){
                String valueI = (String) resOp.get(key);
                System.out.print("已有值：" + valueI);
            }else {
                resOp.set(key,value);
                String valueO = (String) resOp.get(key);
            System.out.print("已存值：" + valueO);
        }
        return null;
    }

    @LogAnnotation
    @ApiOperation("无权访问-login")
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Object unauthorized() {
        return renderError(ExceptionEnum.AUTHORIZATION_EXCEPTION.value());
    }


}
