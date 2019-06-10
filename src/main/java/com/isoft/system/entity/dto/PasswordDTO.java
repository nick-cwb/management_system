package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PasswordDTO {

    @ApiModelProperty(value="用户id")
    private Integer userId;

    @ApiModelProperty(value="旧密码")
    private String oldPassword;

    @ApiModelProperty(value="新密码")
    private String newPassword;

}
