package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WorkbenchDTO {

    @ApiModelProperty(value="用户id")
    private Integer userId;

    @ApiModelProperty(value="菜单id")
    private Integer[] menuIds;

}
