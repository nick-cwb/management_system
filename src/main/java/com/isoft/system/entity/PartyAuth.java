package com.isoft.system.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 权限参与表
 */
@Data
@TableName("party_auth")
public class PartyAuth {

    @ApiModelProperty(value="id")
    @TableField("id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value="参与类型：员工emp、机构org、岗位position")
    @TableField("party_type")
    private String partyType;

    @ApiModelProperty(value="参与类型对应的id")
    @TableField("party_id")
    private Integer partyId;

    @ApiModelProperty(value="角色对应的id")
    @TableField("role_id")
    private Integer roleId;

    @ApiModelProperty(value="角色类型")
    @TableField("role_type")
    private String roleType;

    @ApiModelProperty(value="创建人")
    @TableField("created_emp")
    private Integer createdEmp;

    @ApiModelProperty(value="创建日期")
    @TableField("created_time")
    private Date createdTime;
}
