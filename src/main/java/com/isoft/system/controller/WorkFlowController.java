package com.isoft.system.controller;

import com.isoft.system.common.BaseController;
import com.isoft.system.entity.dto.ApprovalDTO;
import com.isoft.system.entity.dto.LeaveProcessDTO;
import com.isoft.system.entity.vo.TaskPage;
import com.isoft.system.entity.vo.TaskVO;
import com.isoft.system.service.IWorkFlowService;
import org.activiti.engine.task.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/flow")
public class WorkFlowController extends BaseController {

    @Resource
    IWorkFlowService service;

    /**
     * 启动流程实例
     * @param request
     * @param response
     * @param leaveProcessDTO
     * @return
     */
    @PostMapping(value = "/process/start",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object startProcess(HttpServletRequest request, HttpServletResponse response, @RequestBody LeaveProcessDTO leaveProcessDTO) throws Exception{
        boolean flag = service.startProcessInstance(leaveProcessDTO);
        return renderSuccess(flag);
    }

    /**
     * 查询个人任务列表
     * @param request
     * @param response
     * @param assignee
     * @return
     */
    @GetMapping(value = "/task/{assignee}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findAllTask(HttpServletRequest request, HttpServletResponse response,@PathVariable String assignee,@RequestParam(value = "current", defaultValue = "1")Integer current,@RequestParam(value = "size", defaultValue = "10")Integer size){
        TaskPage taskPage = service.findTasks(assignee,current,size);
        return renderSuccess(taskPage);
    }

    /**
     * 根据流程实例
     * @param request
     * @param response
     * @param processInstanceId
     * @return
     */
    @GetMapping(value = "/task/process",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findTaskByProcessInstanceId(HttpServletRequest request, HttpServletResponse response,String processInstanceId){
        TaskVO task = service.findTaskByprocessInstanceId(processInstanceId);
        return renderSuccess(task);
    }

    //完成个人任务
    @PostMapping(value = "/task",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object completeTask(HttpServletRequest request, HttpServletResponse response, @RequestBody ApprovalDTO approvalDTO) throws Exception{
        boolean flag = service.complete(approvalDTO);
        return renderSuccess(flag);
    }


    //完成个人任务
    @PostMapping(value = "/endFlow",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object endFlow(HttpServletRequest request, HttpServletResponse response, @RequestBody ApprovalDTO approvalDTO) throws Exception{
        boolean flag = service.endFlow(approvalDTO);
        return renderSuccess(flag);
    }


    /**
     * 查询流程详情
     * @param request
     * @param response
     * @param taskId
     * @return
     */
    @GetMapping(value = "/task/comment/{taskId}",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findFlowComment(HttpServletRequest request, HttpServletResponse response,@PathVariable String taskId){
        Object object = service.getComments(taskId);
        return renderSuccess(object);
    }

    //查询历史任务
    @GetMapping(value = "/task/historic",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Object findHistoryTask(HttpServletRequest request, HttpServletResponse response,String taskAssignee ,@RequestParam(value = "current", defaultValue = "1")Integer current,@RequestParam(value = "size", defaultValue = "10")Integer size) throws Exception{
        TaskPage taskPage = service.findHistoryTask(taskAssignee,current,size);
        return renderSuccess(taskPage);
    }

}
