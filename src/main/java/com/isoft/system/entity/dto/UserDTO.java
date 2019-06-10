package com.isoft.system.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDTO extends Page<User> {

    @ApiModelProperty(value="用户名")
    private String name;

    @ApiModelProperty(value="机构id")
    private Integer orgId;

    @ApiModelProperty(value="岗位id")
    private Integer poiId;

}
