package com.isoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.AuthButton;
import com.isoft.system.entity.dto.AuthButtonDTO;
import com.isoft.system.entity.dto.ButtonDTO;
import com.isoft.system.enums.ResultEnum;
import com.isoft.system.service.IAuthButtonService;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/button")
public class AuthButtonController extends BaseController {

    @Resource
    IAuthButtonService service;

    /**
     * 查询全部按钮,分页
     * @param request
     * @param response
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询全部按钮,分页")
    @GetMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllByMenuCode(HttpServletRequest request, HttpServletResponse response, AuthButtonDTO authButtonDTO){
        IPage<AuthButton> authButtonPage = service.findAll(authButtonDTO);
        return renderSuccess(authButtonPage);
    }

    @LogAnnotation
    @ApiOperation("查询全部按钮")
    @GetMapping(value = "/menu",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAll(HttpServletRequest request, HttpServletResponse response, ButtonDTO buttonDTO){
        List<AuthButton> authButtonPage = service.findAllMenu(buttonDTO);
        return renderSuccess(authButtonPage);
    }



    /**
     * 查询单个按钮
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询单个按钮")
    @GetMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAuthButton(HttpServletRequest request, HttpServletResponse response,@PathVariable String id){
        AuthButton authButton = service.getById(id);
        if(authButton == null){
            return renderComplete(ResultEnum.NONE_RESULT.value());
        }
        return renderSuccess(authButton);
    }

    /**
     * 新增按钮
     * @param request
     * @param response
     * @param authButton
     * @return
     */
    @LogAnnotation
    @ApiOperation("新增按钮")
    @PostMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object addAuthButton(HttpServletRequest request, HttpServletResponse response,@RequestBody AuthButton authButton) throws Exception {
        boolean result = service.saveButton(authButton);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }

    /**
     * 修改按钮
     * @param request
     * @param response
     * @param authButton
     * @return
     */
    @LogAnnotation
    @ApiOperation("修改按钮")
    @PutMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateAuthButton(HttpServletRequest request, HttpServletResponse response, @RequestBody AuthButton authButton){
        boolean result = service.updateButton(authButton);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }


    /**
     * 删除按钮
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("删除按钮")
    @DeleteMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteAuthButton(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){
        boolean result = service.removeById(id);
        if(!result){
            return renderError(ResultEnum.DELETE_FAILURE.value());
        }
        return renderSuccess(ResultEnum.DELETE_SUCCESS.value());
    }


}
