package com.isoft.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.AuthMenu;
import com.isoft.system.entity.DataAuth;
import com.isoft.system.entity.dto.AuthMenuDTO;
import com.isoft.system.enums.ResultEnum;
import com.isoft.system.service.IAuthMenuService;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class AuthMenuController extends BaseController {

    @Resource
    IAuthMenuService service;

    /**
     * 查询全部菜单,分页
     * @param request
     * @param response
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询全部菜单,分页")
    @GetMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAll(HttpServletRequest request, HttpServletResponse response,AuthMenuDTO authMenuDTO){
        IPage<AuthMenu> menuIPage = service.findAll(authMenuDTO);
        return renderSuccess(menuIPage);
    }

    @GetMapping(value = "/all",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllMenu(HttpServletRequest request, HttpServletResponse response){
//        List<AuthMenu> list = service.list(new QueryWrapper<AuthMenu>().eq("is_del",0));
        List<AuthMenu> list = service.list();
        return renderSuccess(list);
    }

    /**
     * 数据权限
     * @param request
     * @param response
     * @param dataAuth
     * @return
     */
    @GetMapping(value = "/role",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllAuth(HttpServletRequest request, HttpServletResponse response,DataAuth dataAuth){
        List<AuthMenu> menuse = service.findAllAuth(dataAuth.getRoleId(),dataAuth.getMenuId());
        return renderSuccess(menuse);
    }




    /**
     * 查询全部菜单,树形
     * @param request
     * @param response
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询全部菜单,树形")
    @GetMapping(value="/tree",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllTree(HttpServletRequest request, HttpServletResponse response){
        Object object = service.findMenuTree();
        return renderSuccess(object);
    }


    /**
     * 查询单个菜单
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询单个菜单")
    @GetMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findMenu(HttpServletRequest request, HttpServletResponse response,@PathVariable String id){
        AuthMenu authMenu = service.getById(id);
        if(authMenu == null){
            return renderComplete(ResultEnum.NONE_RESULT.value());
        }
        return renderSuccess(authMenu);
    }

    /**
     * 新增菜单
     * @param request
     * @param response
     * @param authMenu
     * @return
     */
    @LogAnnotation
    @ApiOperation("新增菜单")
    @PostMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object addMenu(HttpServletRequest request, HttpServletResponse response,@RequestBody AuthMenu authMenu) throws Exception {
        boolean result = service.saveAuthMenu(authMenu);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }

    /**
     * 修改菜单
     * @param request
     * @param response
     * @param authMenu
     * @return
     */
    @LogAnnotation
    @ApiOperation("修改菜单")
    @PutMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateMenu(HttpServletRequest request, HttpServletResponse response,@RequestBody AuthMenu authMenu) throws Exception{
        boolean result = service.updateAuthMenu(authMenu);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }


    /**
     * 删除菜单
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("删除菜单")
    @DeleteMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteMenu(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){
        boolean result = service.removeById(id);
        if(!result){
            return renderError(ResultEnum.DELETE_FAILURE.value());
        }
        return renderSuccess(ResultEnum.DELETE_SUCCESS.value());
    }
}
