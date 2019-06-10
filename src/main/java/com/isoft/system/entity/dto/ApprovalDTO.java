package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ApprovalDTO {

    @ApiModelProperty("流程实例id")
    String processInstanceId;

    @ApiModelProperty("审批内容")
    String taskId;

    @ApiModelProperty("审批人id")
    Integer userId;

    @ApiModelProperty("审批人")
    String userName;

    @ApiModelProperty("请假天数")
    Integer day;

    @ApiModelProperty("审批内容")
    String content;

    @ApiModelProperty("审批时间")
    Date approvalTime;
}
