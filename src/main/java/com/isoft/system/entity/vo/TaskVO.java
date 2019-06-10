package com.isoft.system.entity.vo;

import lombok.Data;
import net.sf.json.JSONObject;

import java.util.Date;

@Data
public class TaskVO{

    private String assignee;
    private String name;
    private Integer priority;
    private Date createTime;
    private String executionId;
    private String processInstanceId;
    private String processDefinitionId;
    private String taskDefinitionKey;
    private String id;
    private JSONObject content;
    private String day;

}
