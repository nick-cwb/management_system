package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 处理角色权限资源
 */

@Data
public class PartyAuthResourceDTO {

    @ApiModelProperty(value="参与id")
    private Integer id;

    @ApiModelProperty(value="参与类型")
    private String partyType;

    @ApiModelProperty(value="角色id")
    private Integer[] roleIds;

    
}
