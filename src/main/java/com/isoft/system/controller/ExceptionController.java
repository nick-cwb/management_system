package com.isoft.system.controller;

import com.isoft.system.common.BaseController;
import com.isoft.system.common.JsonResult;
import com.isoft.system.enums.ExceptionEnum;
import com.isoft.system.exception.BusinessException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * controller层的统一异常处理
 */

@ControllerAdvice
public class ExceptionController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(ExceptionController.class);

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(ShiroException.class)
    public JsonResult handle401(ShiroException e) {
        log.error("ShiroException:",e);
        return renderError(e.getMessage());
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public JsonResult handle401(UnauthorizedException e) {
        log.error("UnauthorizedException:",e);
        return renderError(ExceptionEnum.UNAUTHORIZED_EXCEPTION.value());
    }


    // 捕捉业务处理异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public JsonResult handleBusinessException(BusinessException e) {
        log.error("BusinessException:",e);
        return renderError(e.getMessage());
    }

    // 捕捉其他所有异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonResult globalException(Throwable ex) {
        log.error("Exception:",ex);
        return renderError(ex.getMessage());
    }



}

