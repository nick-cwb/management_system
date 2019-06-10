package com.isoft.system.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;

@Controller
public class BaseController {

    private static String SUCCESS = "success";
    private static String ERROR = "error";
    private static String COMPLETE = "complete";

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    /**
     * 请求成功
     *
     * @return result
     */
    protected JsonResult renderSuccess() {
        JsonResult result = new JsonResult();
        result.setResultStatus(SUCCESS);
        return result;
    }
    protected JsonResult renderSuccess(String message) {
        JsonResult result = renderSuccess();
        result.setMessage(message);
        return result;
    }
    protected JsonResult renderSuccess(Object object) {
        JsonResult result = renderSuccess();
        result.setData(object);
        return result;
    }
    protected JsonResult renderSuccess(HttpServletResponse response, HttpStatus status) {
        JsonResult result = renderSuccess();
        response.setStatus(status.value());
        result.setMessage(status.getReasonPhrase());
        return result;
    }

    /**
     * 请求失败
     *
     * @return result
     */
    protected JsonResult renderError() {
        JsonResult result = new JsonResult();
        result.setResultStatus(ERROR);
        return result;
    }
    protected JsonResult renderError(String message) {
        JsonResult result = renderError();
        result.setMessage(message);
        return result;
    }
    protected JsonResult renderError(Object object) {
        JsonResult result = renderError();
        result.setData(object);
        return result;
    }
    protected JsonResult renderError(HttpServletResponse response, HttpStatus status) {
        JsonResult result = renderError();
        response.setStatus(status.value());
        result.setMessage(status.getReasonPhrase());
        return result;
    }

    /**
     * 请求完成：用于异步的数据处理
     *
     * @return result
     */
    protected JsonResult renderComplete() {
        JsonResult result = new JsonResult();
        result.setResultStatus(COMPLETE);
        return result;
    }
    protected JsonResult renderComplete(String message) {
        JsonResult result = renderComplete();
        result.setMessage(message);
        return result;
    }
    protected JsonResult renderComplete(Object object) {
        JsonResult result = renderComplete();
        result.setData(object);
        return result;
    }
    protected JsonResult renderComplete(HttpServletResponse response, HttpStatus status) {
        JsonResult result = renderComplete();
        response.setStatus(status.value());
        result.setMessage(status.getReasonPhrase());
        return result;
    }

}
