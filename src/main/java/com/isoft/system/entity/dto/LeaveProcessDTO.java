package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LeaveProcessDTO {

    @ApiModelProperty(value="申请人")
    private Integer userId;

    @ApiModelProperty(value="流程id")
    private String processId;

    @ApiModelProperty(value="请假天数")
    private Integer day;

    @ApiModelProperty("审批内容")
    String content;
}
