package com.isoft.system.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.isoft.system.entity.VacationDays;
import com.isoft.system.entity.dto.ApprovalDTO;
import com.isoft.system.entity.dto.LeaveProcessDTO;
import com.isoft.system.entity.vo.TaskPage;
import com.isoft.system.entity.vo.TaskVO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.service.IUserService;
import com.isoft.system.service.IVacationDaysService;
import com.isoft.system.service.IWorkFlowService;
import com.isoft.system.utils.Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@DS("master")
public class WorkFlowServiceImpl implements IWorkFlowService {

    @Autowired
    ProcessEngine processEngine;
    //提供一系列管理流程部署和流程定义的API。
    @Autowired
    RepositoryService repositoryService;
    //在流程运行时对流程实例进行管理与控制。
    @Autowired
    RuntimeService runtimeService;
    //对流程的历史数据进行操作，包括查询、删除这些历史数据。
    @Autowired
    HistoryService historyService;
    //对流程任务进行管理，例如任务提醒、任务完成和创建任务等。
    @Autowired
    TaskService taskService;
    //提供对流程角色数据进行管理的API，这些角色数据包括用户组、用户及它们之间的关系。
    @Autowired
    IdentityService identityService;
    //提供对流程引擎进行管理和维护的服务。
    @Autowired
    ManagementService managementService;
    @Resource
    IUserService userService;
    @Resource
    IVacationDaysService vacationDaysService;

    /**
     * 方法说明 ： 根据流程定义Id查询最新流程定义.
     * @param processDefinitionId
     *            流程定义Id
     */
    @Override
    public ProcessDefinition findProcessDefinitionByPrcDefId(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).orderByProcessDefinitionVersion().desc().singleResult();
        return processDefinition;
    }


    /**
     * 方法说明 ： 根据流程定义Id查询流程定义.
     * @param processDefinitionId
     *            流程定义Id
     */
    @Override
    public ProcessDefinitionEntity findProcessDefinitionEntityByProcDefId(String processDefinitionId) {
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
        return processDefinitionEntity;
    }

    /**
     * 方法说明 ： 根据流程实例Id查询流程实例.
     * @param processInstanceId
     *            流程实例Id
     */
    @Override
    public ProcessInstance findProcessInstanceByProcInst(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 根据流程实例Id查询流程实例.
     * @param processInstanceId
     */
    @Override
    public Execution findExecutionByProcInst(String processInstanceId) {
        return runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 方法说明 ： 根据实例Id查询任务. 返回多个任务
     * @param processInstanceId
     *            实例Id
     */
    @Override
    public List<Task> findTaskByProcInstIds(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).orderByTaskDefinitionKey().asc().list();
    }

    /**
     * 获取当前流程实例 当前节点的历史任务信息
     * @param processInstanceId 流程实例id
     * @param taskDefinitionKey 任务环节id （key）
     * @return 返回历史任务实例信息
     * @throws Exception
     */
    @Override
    public List<HistoricTaskInstance> getHistoricTaskInstance(String processInstanceId, String taskDefinitionKey) {
        List<HistoricTaskInstance> historicTaskInstances = new ArrayList<HistoricTaskInstance>();
        List<HistoricTaskInstance> instances = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).taskDefinitionKey(taskDefinitionKey).orderByHistoricTaskInstanceEndTime().desc().list();
        if(instances!=null){
            HistoricTaskInstance instance = instances.get(0);//只取最新的
            historicTaskInstances.add(instance);
            List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery().taskParentTaskId(instance.getId()).list();//查询子历史任务 并添加到历史任务信息中
            if(taskInstances!=null){
                for(HistoricTaskInstance historicTaskInstance : taskInstances){
                    historicTaskInstances.add(historicTaskInstance);
                }
            }
        }
        return historicTaskInstances;
    }

    /**
     * @param leaveProcessDTO
     * @return
     */
    @Override
    public boolean startProcessInstance(LeaveProcessDTO leaveProcessDTO) throws Exception{
        //请假时先查验调休余额是否大于请假天数
        VacationDays vacationDays = vacationDaysService.getById(leaveProcessDTO.getUserId());
        //判断如果请假天数大于剩余假期返回业务异常
        if(vacationDays == null || vacationDays.getRemainderDays()<leaveProcessDTO.getDay()){
            throw new BusinessException(BusinessExceptionEnum.VACATION_DAYS_NOT_ENOUGH.value());
        }
        Map<String, Object> variables = new HashMap<String, Object>();
        //设置流程变量
        variables.put("day",leaveProcessDTO.getDay());
        //使用流程定义的key启动实例，key对应bpmn文件中id的属性值，默认按照最新版本流程启动
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(leaveProcessDTO.getProcessId(),variables);
        //获取实例流程的第一个任务
        TaskVO task = this.findTaskByprocessInstanceId(pi.getProcessInstanceId());
        //设置审批内容
        ApprovalDTO approvalDTO = new ApprovalDTO();
        approvalDTO.setUserId(leaveProcessDTO.getUserId());
        approvalDTO.setUserName(userService.getById(leaveProcessDTO.getUserId()).getName());
        approvalDTO.setDay(leaveProcessDTO.getDay());
        approvalDTO.setApprovalTime(Util.createTimestamp());
        approvalDTO.setTaskId(task.getId());
        approvalDTO.setContent(leaveProcessDTO.getContent());
        JSONObject jsonObject = JSONObject.fromObject(approvalDTO);
        //申请人为第一个任务完成人
        taskService.addComment(task.getId(),task.getProcessInstanceId(),jsonObject.toString());
        taskService.complete(task.getId());
        return true;
    }

    /**
     * 查询所有流程定义
     * @return
     */
    @Override
    public List<ProcessDefinitionEntityImpl> findProcessDefinition() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义查询
                /*指定查询条件,where条件*/
                //.deploymentId(deploymentId)//使用部署对象ID查询
                //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
                //.processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
                //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /*排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
                //.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
                .list();//返回一个集合列表，封装流程定义
        List<ProcessDefinitionEntityImpl> list1 = new LinkedList<ProcessDefinitionEntityImpl>();
        for (ProcessDefinition pd:list) {
            list1.add((ProcessDefinitionEntityImpl) pd);
        }
        return list1;
    }

    @Override
    public TaskPage findTasks(String assignee, Integer current, Integer size) {
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .orderByTaskCreateTime().desc()
                .taskCandidateOrAssigned(assignee)
                .listPage((current-1)*10,size);
        if (list.size()==0){
            return null;
        }
        List<Task> lists = processEngine.getTaskService()
                .createTaskQuery()
                .taskCandidateOrAssigned(assignee)
                .list();
        int total = lists.size();
        List<TaskVO> voList = new ArrayList<TaskVO>();
        for (Task task:list) {
            List<Comment> list1 = this.getProcessComments(task.getId());
            String str = list1.get(0).getFullMessage();
            TaskVO taskVO = new TaskVO();
            taskVO.setContent(JSONObject.fromObject(str));
            taskVO.setAssignee(task.getAssignee());
            taskVO.setCreateTime(task.getCreateTime());
            taskVO.setExecutionId(task.getExecutionId());
            taskVO.setId(task.getId());
            taskVO.setName(task.getName());
            taskVO.setPriority(task.getPriority());
            taskVO.setProcessDefinitionId(task.getProcessDefinitionId());
            taskVO.setTaskDefinitionKey(task.getTaskDefinitionKey());
            taskVO.setProcessInstanceId(task.getProcessInstanceId());
            voList.add(taskVO);
        }
        TaskPage taskPage = new TaskPage();
        taskPage.setTotal(total);
        taskPage.setTaskVOList(voList);
        return taskPage;
    }

    @Override
    public TaskVO findTaskByprocessInstanceId(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).orderByProcessInstanceId().desc().singleResult();
        TaskVO taskVO = new TaskVO();
        taskVO.setAssignee(task.getAssignee());
        taskVO.setCreateTime(task.getCreateTime());
        taskVO.setExecutionId(task.getExecutionId());
        taskVO.setId(task.getId());
        taskVO.setName(task.getName());
        taskVO.setPriority(task.getPriority());
        taskVO.setProcessDefinitionId(task.getProcessDefinitionId());
        taskVO.setTaskDefinitionKey(task.getTaskDefinitionKey());
        taskVO.setProcessInstanceId(task.getProcessInstanceId());
        return taskVO;
    }

    @Override
    @Transactional
    public boolean complete(ApprovalDTO approvalDTO) throws Exception{
        approvalDTO.setApprovalTime(Util.createTimestamp());
        approvalDTO.setUserName(userService.getById(approvalDTO.getUserId()).getName());
        JSONObject jsonObject = JSONObject.fromObject(approvalDTO);
        //获取当前任务
        //Task task = taskService.createTaskQuery().processInstanceId(approvalDTO.getProcessInstanceId()).orderByProcessInstanceId().desc().singleResult();
        Task task = taskService.createTaskQuery().taskId(approvalDTO.getTaskId()).singleResult();
        //为空表示已经完成审批
        if( task != null && task.getName().equals("总任务")){
            //从历史任务批注中获取请假人请假天数
            JSONArray jsonArray = (JSONArray) this.getComments(approvalDTO.getTaskId());
            JSONObject jsonObj = (JSONObject) jsonArray.get(0);
            Integer day = (Integer) jsonObj.get("day");
            Integer userId = (Integer) jsonObj.get("userId");
            //获取调休后再修改剩余请假天数
            VacationDays vacationDays = vacationDaysService.getById(userId);
            Integer remainderDays = vacationDays.getRemainderDays() - day;
            vacationDays.setRemainderDays(remainderDays);
            boolean flag = vacationDaysService.updateById(vacationDays);
        }
        taskService.addComment(approvalDTO.getTaskId(),approvalDTO.getProcessInstanceId(),jsonObject.toString());
        taskService.complete(approvalDTO.getTaskId());
        return true;
    }

    @Override
    public boolean endFlow(ApprovalDTO approvalDTO) {
        runtimeService.deleteProcessInstance(approvalDTO.getProcessInstanceId(), approvalDTO.getContent());
        return true;
    }

    @Override
    public List<Comment> getProcessComments(String taskId) {
//         1) 获取流程实例的ID
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance pi =runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
//       2）通过流程实例查询所有的(用户任务类型)历史活动
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery().processInstanceId(pi.getId()).activityType("userTask").list();
//       3）查询每个历史任务的批注
        List<Comment> historyCommnets = this.getCommentFromHiList(hais);
        return historyCommnets;
    }

    /**
     * 获取历史任务 批注
     * @param taskId
     * @return
     */
    public List<Comment> getProcessCommentsHi(String taskId) {
//         1) 获取流程实例的ID
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        HistoricProcessInstance pi =historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
//       2）通过流程实例查询所有的(用户任务类型)历史活动
        List<HistoricActivityInstance> hais = historyService.createHistoricActivityInstanceQuery().processInstanceId(pi.getId()).activityType("userTask").list();
//       3）查询每个历史任务的批注
        List<Comment> historyCommnets = this.getCommentFromHiList(hais);
        return historyCommnets;
    }


        @Override
    public TaskPage findHistoryTask(String taskAssignee, Integer current, Integer size) {
        List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                .orderByTaskCreateTime().desc()
                .taskAssignee(taskAssignee)//指定历史任务的办理人
                .listPage((current-1)*10,size);
        List<HistoricTaskInstance> lists = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                    .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                    .taskAssignee(taskAssignee)//指定历史任务的办理人
                    .list();
        int total = lists.size();
        List<TaskVO> voList = new ArrayList<TaskVO>();
        for (HistoricTaskInstance task:list) {
            List<Comment> list1 = this.getProcessCommentsHi(task.getId());
            String str = list1.get(0).getFullMessage();
            TaskVO taskVO = new TaskVO();
            taskVO.setContent(JSONObject.fromObject(str));
            taskVO.setAssignee(task.getAssignee());
            taskVO.setCreateTime(task.getCreateTime());
            taskVO.setExecutionId(task.getExecutionId());
            taskVO.setId(task.getId());
            taskVO.setName(task.getName());
            taskVO.setPriority(task.getPriority());
            taskVO.setProcessDefinitionId(task.getProcessDefinitionId());
            taskVO.setTaskDefinitionKey(task.getTaskDefinitionKey());
            taskVO.setProcessInstanceId(task.getProcessInstanceId());
            voList.add(taskVO);
        }
        TaskPage taskPage = new TaskPage();
        taskPage.setTotal(total);
        taskPage.setTaskVOList(voList);
        return taskPage;
    }

    @Override
    public Object getComments(String taskId) {
        List<Comment> list = this.getProcessComments(taskId);
        JSONArray jsonArray = new JSONArray();
        for (Comment comment:list) {
            String str = comment.getFullMessage();
            JSONObject jsonObject = JSONObject.fromObject(str);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    private List<Comment> getCommentFromHiList(List<HistoricActivityInstance> hais ){
        List<Comment> historyCommnets = new ArrayList<>();
        for (HistoricActivityInstance hai : hais) {
            String historytaskId = hai.getTaskId();
            List<Comment> comments = taskService.getTaskComments(historytaskId);
            // 4）如果当前任务有批注信息，添加到集合中
            if(comments!=null && comments.size()>0){
                historyCommnets.addAll(comments);
            }
        }
        return historyCommnets;
    }

}
