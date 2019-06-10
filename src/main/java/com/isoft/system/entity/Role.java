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
@TableName("role")
public class Role {


    @ApiModelProperty(value="角色id")
    @TableField("id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value="角色编码")
    @TableField("role_code")
    private String roleCode;

    @ApiModelProperty(value="角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty(value="角色描述")
    @TableField("role_desc")
    private String roleDesc;

    @ApiModelProperty(value="创建时间")
    @TableField("created_at")
    private Date createdAt;

    @ApiModelProperty(value="创建人")
    @TableField("created_emp")
    private Integer createdEmp;

    @ApiModelProperty(value="逻辑删除")
    @TableLogic
    @TableField("is_del")
    private Integer isDel;

}
