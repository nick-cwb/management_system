package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 处理角色授权资源
 */

@Data
public class RoleResourceDTO {

    @ApiModelProperty(value="用户id")
    private Integer userId;

    @ApiModelProperty(value="角色id")
    private Integer roleId;

    @ApiModelProperty(value="菜单id")
    private Integer[] menuIds;

    @ApiModelProperty(value="按钮id")
    private Integer[] buttonIds;
}
