package com.isoft.system.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源权限表
 */
@Data
@TableName("res_auth")
public class ResAuth {

    @ApiModelProperty(value="主键")
    private int id;

    @ApiModelProperty(value="参与id:角色对应的id")
    @TableField("role_id")
    private Integer roleId;

    @ApiModelProperty(value="资源id")
    @TableField("res_id")
    private Integer resId;

    @ApiModelProperty(value="资源类型:1、菜单资源menu2、按钮资源btn（暂定这两种）")
    @TableField("res_type")
    private String resType;

    @ApiModelProperty(value="资源状态：0、正1、注销")
    @TableLogic
    @TableField("res_state")
    private String resState;

    @ApiModelProperty(value="创建的用户")
    @TableField("created_emp")
    private Integer createdEmp;

    @ApiModelProperty(value="创建时间")
    @TableField("created_time")
    private Date createdTime;
}
