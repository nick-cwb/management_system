package com.isoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.DataAuth;
import com.isoft.system.entity.dto.BaseDataDTO;
import com.isoft.system.entity.dto.DataAuthDTO;
import com.isoft.system.enums.ResultEnum;
import com.isoft.system.service.IDataAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/dataAuth")
public class DataAuthController extends BaseController {

    @Resource
    IDataAuthService service;

    @GetMapping(value = "/all",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllData(HttpServletRequest request, HttpServletResponse response, DataAuthDTO dataAuthDTO){
        IPage<DataAuth> page = service.findAll(dataAuthDTO);
        return renderSuccess(page);
    }




        /**
         * 根据menuid和roleID查询用户
         *
         * @param request
         * @param response
         * @param dataAuth
         * @return
         */
    @GetMapping(produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object findDataAuth(HttpServletRequest request, HttpServletResponse response, DataAuth dataAuth) {
        DataAuth data = service.findDataAuthByID(dataAuth.getMenuId(), dataAuth.getRoleId());
        return renderSuccess(data);
    }


    /**
     * 新增数据权限
     *
     * @param request
     * @param response
     * @param dataAuth
     * @return
     */
    @PostMapping(produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object addDataAuth(HttpServletRequest request, HttpServletResponse response, @RequestBody DataAuth dataAuth) {
        boolean flag = service.save(dataAuth);
        return renderSuccess(ResultEnum.ADD_SUCCESS.value());
    }

    /**
     * 修改数据权限项
     * @param request
     * @param response
     * @param dataAuth
     * @return
     */
    @PutMapping(produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object updDataAuth(HttpServletRequest request, HttpServletResponse response, @RequestBody DataAuth dataAuth) {
        boolean flag = service.updateById(dataAuth);
        return renderSuccess(ResultEnum.UPDATE_SUCCESS.value());
    }


    /**
     * 删除数据权限项
     * @param request
     * @param response
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object delDataAuth(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) {
        boolean flag = service.removeById(id);
        return renderSuccess(ResultEnum.DELETE_SUCCESS.value());
    }
}