package com.isoft.system.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserAuthVO {

    @ApiModelProperty(value="菜单树")
    private Object menu;

    @ApiModelProperty(value="按钮")
    private Object button;


}
