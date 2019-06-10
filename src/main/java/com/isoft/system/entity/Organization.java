package com.isoft.system.entity;


import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  组织机构
 */
@Data
@TableName("organization")
public class Organization {

    @ApiModelProperty(value="id")
    @TableField("id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value="父级id")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value="机构编号")
    @TableField("org_code")
    private String orgCode;

    @ApiModelProperty(value="机构名称")
    @TableField("org_name")
    private String orgName;

    @ApiModelProperty(value="机构级别")
    @TableField("org_level")
    private Integer orgLevel;

    @ApiModelProperty(value="机构类型：1总公司 2总公司部门 3分公司 4分公司部门")
    @TableField("org_type")
    private String orgType;

    @ApiModelProperty(value="机构排序号")
    @TableField("sort_no")
    private Integer sortNo;

    @ApiModelProperty(value="机构状态：0正常 1注销")
    @TableLogic
    private Integer status;


    @ApiModelProperty(value="创建日期")
    @TableField("created_at")
    private Date createdAt;

    @ApiModelProperty(value="修改日期")
    @TableField("last_update")
    private Date lastUpdate;

    @ApiModelProperty(value="备注")
    private String remark;

    @ApiModelProperty(value="生效时间")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty(value="失效时间")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty(value="机构树形子节点")
    @TableField(exist = false)
    private List<Organization> children;
}
