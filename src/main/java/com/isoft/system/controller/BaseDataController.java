package com.isoft.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.annotation.LogAnnotation;
import com.isoft.system.common.BaseController;
import com.isoft.system.entity.BaseDataOption;
import com.isoft.system.entity.MultiData;
import com.isoft.system.entity.dto.BaseDataDTO;
import com.isoft.system.enums.ResultEnum;
import com.isoft.system.service.IBaseDataService;
import com.isoft.system.service.IMultiDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/base")
public class BaseDataController extends BaseController {

    @Resource
    IBaseDataService service;
    @Resource
    IMultiDataService multiDataService;


    @GetMapping(value = "/multi",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findMultiData(HttpServletRequest request, HttpServletResponse response){
        List<MultiData> list = multiDataService.findAll();
        return renderSuccess(list);
    }


    /*@LogAnnotation
    @ApiOperation("根据父id获取子选项")*/
    @GetMapping(value = "/option/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findChildOption(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id){
        List<BaseDataOption> optionList = service.findOption(id);
        return renderSuccess(optionList);
    }

    @GetMapping(value = "/option/all",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllOption(HttpServletRequest request, HttpServletResponse response, BaseDataDTO baseDataDTO){
        IPage<BaseDataOption> page = service.findAll(baseDataDTO);
        return renderSuccess(page);
    }


  /*  @GetMapping(value = "/option/code",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllOptionByCode(HttpServletRequest request, HttpServletResponse response, String codes){
        IPage<BaseDataOption> page = service.findAll(baseDataDTO);
        return renderSuccess(page);
    }
*/


    /*  @LogAnnotation
    @ApiOperation("新增基础数据")*/
    @PostMapping(value = "/option",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object addDataOption(HttpServletRequest request, HttpServletResponse response, @RequestBody BaseDataOption option){
        boolean flag = service.saveOption(option);
        return renderSuccess(ResultEnum.ADD_SUCCESS.value());
    }


/*    @LogAnnotation
    @ApiOperation("修改基础数据")*/
    @PutMapping(value = "/option",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object updDataOption(HttpServletRequest request, HttpServletResponse response, @RequestBody BaseDataOption option){
        boolean flag = service.updOption(option);
        return renderSuccess(ResultEnum.UPDATE_SUCCESS.value());
    }

/*    @LogAnnotation
    @ApiOperation("根据id删除基础数据")*/
    @DeleteMapping(value = "/option/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object delDataOption(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id){
        boolean flag = service.delOption(id);
        return renderSuccess(ResultEnum.DELETE_SUCCESS.value());
    }




}
