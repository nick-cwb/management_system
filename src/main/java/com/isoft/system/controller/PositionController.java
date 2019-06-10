package com.isoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.Position;
import com.isoft.system.entity.dto.PositionDTO;
import com.isoft.system.enums.ResultEnum;
import com.isoft.system.service.IPositionService;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/position")
public class PositionController extends BaseController {

    @Resource
    IPositionService service;

    /**
     * 查询全部岗位,分页
     * @param request
     * @param response
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询全部岗位,分页")
    @GetMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAll(HttpServletRequest request, HttpServletResponse response, PositionDTO positionDTO){
        IPage<Position> organizationPage = service.findAll(positionDTO);
        return renderSuccess(organizationPage);
    }



    /**
     * 查询单个岗位
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("查询单个岗位")
    @GetMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findOrganization(HttpServletRequest request, HttpServletResponse response,@PathVariable String id){
        Position position = service.getById(id);
        if(position == null){
            return renderComplete(ResultEnum.NONE_RESULT.value());
        }
        return renderSuccess(position);
    }

    /**
     * 新增岗位
     * @param request
     * @param response
     * @param position
     * @return
     */
    @LogAnnotation
    @ApiOperation("新增岗位")
    @PostMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object addOrganization(HttpServletRequest request, HttpServletResponse response,@RequestBody Position position) throws Exception {
        boolean result = service.savePosition(position);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }

    /**
     * 修改岗位
     * @param request
     * @param response
     * @param position
     * @return
     */
    @LogAnnotation
    @ApiOperation("修改岗位")
    @PutMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updateOrganization(HttpServletRequest request, HttpServletResponse response,@RequestBody Position position) throws Exception{
        boolean result = service.updatePosition(position);
        if(!result){
            return renderComplete(response,HttpStatus.UNPROCESSABLE_ENTITY );
        }
        return renderSuccess(response,HttpStatus.CREATED);
    }


    /**
     * 删除岗位
     * @param request
     * @param response
     * @param id
     * @return
     */
    @LogAnnotation
    @ApiOperation("删除岗位")
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
