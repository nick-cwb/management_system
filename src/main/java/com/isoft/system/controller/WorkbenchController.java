package com.isoft.system.controller;

import com.isoft.system.common.BaseController;
import com.isoft.system.entity.dto.WorkbenchDTO;
import com.isoft.system.service.IWorkbenchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/workbench")
public class WorkbenchController extends BaseController {

    @Resource
    IWorkbenchService service;


    /**
     * 根据用户id获取当前用户保存的工作台页面
     * @param request
     * @param response
     * @param id
     * @return
     */
    @GetMapping(value="/{id}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findWorkbench(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) throws Exception{
        Object obj = service.findWorkbenchByUserID(id);
        return renderSuccess(obj);
    }


    @PostMapping(produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findUser(HttpServletRequest request, HttpServletResponse response, @RequestBody WorkbenchDTO workbenchDTO) throws Exception{
        Object obj = service.updateWorkbench(workbenchDTO);
        return renderSuccess(obj);
    }

}
