package com.isoft.system.entity.vo;

import com.isoft.system.entity.AuthButton;
import com.isoft.system.entity.AuthMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 返回的资源集合
 */

@Data
public class RoleResourceVO {

    @ApiModelProperty(value="菜单集合")
    private List<AuthMenu> menuList;

    @ApiModelProperty(value="按钮集合")
    private List<AuthButton> buttonList;

}
