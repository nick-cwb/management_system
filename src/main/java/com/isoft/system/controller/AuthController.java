package com.isoft.system.controller;

import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.dto.RoleResourceDTO;
import com.isoft.system.service.IAuthService;

import io.swagger.annotations.ApiOperation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权公共接口
 */

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Resource
    IAuthService service;

    /**
     * 根据用户获取对应的菜单及按钮权限
     */
    @LogAnnotation
    @ApiOperation("根据用户获取对应的菜单及按钮权限")
    @GetMapping(value="/user",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUserAuth (HttpServletRequest request, HttpServletResponse response){
        Object object = service.findUserAuth(null);
        return renderSuccess(object);
    }

    /**
     * 根据用户获取对应的菜单及按钮权限
     */
    @GetMapping(value="/user/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUserAuths (HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){
        Object object = service.findUserAuths(id);
        return renderSuccess(object);
    }

    /**
     * 根据用户id查询当前用户关联的全部角色
     * @param request
     * @param response
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value="/role/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUserRole(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id){
        Object object = service.findUserRole(id);
        return renderSuccess(object);
    }

    /**
     * 根据角色id查询对应的菜单、按钮权限信息
     * @param request
     * @param response
     * @param id
     * @return
     */
    @GetMapping(value="/resource/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findRoleAuth(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){
        Object object = service.findRoleAuth(id);
        return renderSuccess(object);
    }

    /**
     * 编辑角色权限
     * @param request
     * @param response
     * @param resDTO
     * @return
     */
    @LogAnnotation
    @ApiOperation("编辑角色权限")
    @PostMapping(value="/resource",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateRoleAuth(HttpServletRequest request, HttpServletResponse response,@RequestBody RoleResourceDTO resDTO) throws Exception{
        Object object = service.updateRoleAuth(resDTO);
        return renderSuccess(object);
    }

}
