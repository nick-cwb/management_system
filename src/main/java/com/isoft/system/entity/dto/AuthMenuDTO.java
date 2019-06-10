package com.isoft.system.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.AuthMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthMenuDTO extends Page<AuthMenu> {

    @ApiModelProperty(value="菜单名称")
    private String menuName;

    @ApiModelProperty(value="菜单标签")
    private String menuLabel;

    @ApiModelProperty(value="菜单编码")
    private String menuCode;

}
