package com.isoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.Organization;
import com.isoft.system.entity.dto.OrganizationDTO;
import com.isoft.system.enums.ResultEnum;
import com.isoft.system.service.IOrganizationService;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController {

    @Resource
    IOrganizationService service;

    /**
     * 查询全部机构,分页
     * @param request
     * @param response
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询全部机构,分页")
    @GetMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAll(HttpServletRequest request, HttpServletResponse response, OrganizationDTO organizationDTO){
        IPage<Organization> organizationPage = service.findAll(organizationDTO);
        return renderSuccess(organizationPage);
    }

    /**
     * 查询全部机构,树形
     * @param request
     * @param response
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询全部机构,树形")
    @GetMapping(value="/tree",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllTree(HttpServletRequest request, HttpServletResponse response){
        Object object = service.findOrganizationTree();
        return renderSuccess(object);
    }


    /**
     * 查询单个机构
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询单个机构")
    @GetMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findOrganization(HttpServletRequest request, HttpServletResponse response,@PathVariable String id){
        Organization organization = service.getById(id);
        if(organization == null){
            return renderComplete(ResultEnum.NONE_RESULT.value());
        }
        return renderSuccess(organization);
    }

    /**
     * 新增机构
     * @param request
     * @param response
     * @param organization
     * @return
     */
    @LogAnnotation
    @ApiOperation("新增机构")
    @PostMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object addOrganization(HttpServletRequest request, HttpServletResponse response,@RequestBody Organization organization) throws Exception {
        boolean result = service.saveOrganization(organization);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }

    /**
     * 修改机构
     * @param request
     * @param response
     * @param organization
     * @return
     */
    @LogAnnotation
    @ApiOperation("修改机构")
    @PutMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateOrganization(HttpServletRequest request, HttpServletResponse response, @RequestBody Organization organization) throws Exception{
        boolean result = service.updateOrganization(organization);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }


    /**
     * 删除机构
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("删除机构")
    @DeleteMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object deleteOrganization(HttpServletRequest request, HttpServletResponse response,@PathVariable Integer id){
        boolean result = service.removeById(id);
        if(!result){
            return renderError(ResultEnum.DELETE_FAILURE.value());
        }
        return renderSuccess(ResultEnum.DELETE_SUCCESS.value());
    }

}
