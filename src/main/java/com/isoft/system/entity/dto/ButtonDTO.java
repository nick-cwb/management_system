package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ButtonDTO {
    @ApiModelProperty(value="角色id")
    private Integer roleId;

    @ApiModelProperty(value="菜单编码")
    private String menuCode;
}
