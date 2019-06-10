package com.isoft.system.entity.vo;

import com.isoft.system.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于返回用户登录的基本信息
 */
@Data
public class LoginVO {

    @ApiModelProperty(value="请求token")
    private String token;

    @ApiModelProperty(value="登录用户信息")
    private User user;

    public LoginVO() {
    }

    public LoginVO(String token,User user) {
        this.token = token;
        this.user = user;
    }
}
