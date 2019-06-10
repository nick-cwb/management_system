package com.isoft.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("user")
public class User {

    @ApiModelProperty(value="用户id")
    @TableField("id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value="用户名称")
    @TableField("username")
    private String name;

    @ApiModelProperty(value="用户密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value="注册时间")
    @TableField("register_time")
    private Date registerTime;

    @ApiModelProperty(value="上次登录时间")
    @TableField("last_login_time")
    private Date lastLoginTime;

    @ApiModelProperty(value="上次登录ip")
    @TableField("last_login_ip")
    private String lastLoginIp;

    @ApiModelProperty(value="会员状态 0:启用 1:停用")
    @TableLogic
    @TableField("user_status")
    private Integer userStatus;
}
