package com.isoft.system.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleDTO extends Page<Role> {

    @ApiModelProperty(value="角色编码")
    private String roleCode;

    @ApiModelProperty(value="角色名称")
    private String roleName;

}
