package com.isoft.system.service;

import com.isoft.system.entity.dto.ApprovalDTO;
import com.isoft.system.entity.dto.LeaveProcessDTO;
import com.isoft.system.entity.vo.TaskPage;
import com.isoft.system.entity.vo.TaskVO;
import io.swagger.models.auth.In;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import java.util.List;

public interface IWorkFlowService {

    ProcessDefinition findProcessDefinitionByPrcDefId(String processDefinitionId);

    ProcessDefinitionEntity findProcessDefinitionEntityByProcDefId(String processDefinitionId);

    ProcessInstance findProcessInstanceByProcInst(String processInstanceId);

    Execution findExecutionByProcInst(String processInstanceId);

    List<Task> findTaskByProcInstIds(String processInstanceId);

    List<HistoricTaskInstance> getHistoricTaskInstance(String processInstanceId, String taskDefinitionKey);

    boolean startProcessInstance(LeaveProcessDTO leaveProcessDTO) throws Exception;

    List<ProcessDefinitionEntityImpl> findProcessDefinition();

    TaskPage findTasks(String assignee, Integer current, Integer size);

    TaskVO findTaskByprocessInstanceId(String processInstanceId);

    boolean complete(ApprovalDTO approvalDTO) throws Exception;

    boolean endFlow(ApprovalDTO approvalDTO);

    List<Comment> getProcessComments(String taskId);

    TaskPage findHistoryTask(String taskAssignee, Integer current, Integer size);

    Object getComments(String taskId);
}
