package com.isoft.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.Role;
import com.isoft.system.entity.dto.RoleDTO;
import com.isoft.system.enums.ResultEnum;
import com.isoft.system.service.IRoleService;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Resource
    IRoleService service;


    /**
     * 查询全部角色,分页
     * @param request
     * @param response
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询全部角色,分页")
    @GetMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAll(HttpServletRequest request, HttpServletResponse response, RoleDTO roleDTO){
        IPage<Role> userPage = service.findAll(roleDTO);
        return renderSuccess(userPage);
    }



    @GetMapping(value="/all",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllRoles(HttpServletRequest request, HttpServletResponse response){
//        List<Role> list = service.list(new QueryWrapper<Role>().eq("is_del",0));
        List<Role> list = service.list();
        return renderSuccess(list);
    }

    /**
     * 查询单个角色
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询单个角色")
    @GetMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUser(HttpServletRequest request, HttpServletResponse response,@PathVariable String id){
        Role role = service.getById(id);
        if(role == null){
            return renderComplete(ResultEnum.NONE_RESULT.value());
        }
        return renderSuccess(role);
    }

    /**
     * 新增角色
     * @param request
     * @param response
     * @param role
     * @return
     */
    @LogAnnotation
    @ApiOperation("新增角色")
    @PostMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object addUser(HttpServletRequest request, HttpServletResponse response,@RequestBody Role role) throws Exception {
        boolean result = service.saveRole(role);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }

    /**
     * 修改角色
     * @param request
     * @param response
     * @param role
     * @return
     */
    @LogAnnotation
    @ApiOperation("修改角色")
    @PutMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateUser(HttpServletRequest request, HttpServletResponse response,@RequestBody Role role){
        boolean result = service.updateRole(role);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }


    /**
     * 删除角色
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("删除角色")
    @DeleteMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteUser(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){
        boolean result = service.removeById(id);
        if(!result){
            return renderError(ResultEnum.DELETE_FAILURE.value());
        }
        return renderSuccess(ResultEnum.DELETE_SUCCESS.value());
    }


}
