package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 处理员工岗位资源
 */

@Data
public class EmpPositionResourceDTO {

    @ApiModelProperty(value="参与id")
    private Integer id;

    @ApiModelProperty(value="岗位id")
    private Integer[] positionIds;

    
}
