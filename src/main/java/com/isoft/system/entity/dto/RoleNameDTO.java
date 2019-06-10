package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色信息
 */

@Data
public class RoleNameDTO {


    @ApiModelProperty(value="角色id")
    private Integer roleId;

    @ApiModelProperty(value="角色名称")
    private String roleDesc;

}
