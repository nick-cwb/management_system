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
 * 岗位
 */
@Data
@TableName("position")
public class Position {

    @ApiModelProperty(value="岗位id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value="岗位编码")
    @TableField("posi_code")
    private String posiCode;

    @ApiModelProperty(value="岗位名称")
    @TableField("posi_name")
    private String posiName;

    @ApiModelProperty(value="岗位级别")
    @TableField("posi_level")
    private Integer posiLevel;

    @ApiModelProperty(value="机岗位类型")
    private String type;

    @ApiModelProperty(value="创建时间")
    @TableField("created_at")
    private Date createdAt;

    @ApiModelProperty(value="更新时间")
    @TableField("last_update")
    private Date lastUpdate;

    @ApiModelProperty(value="创建人")
    @TableField("update_emp")
    private Integer updateEmp;

    @ApiModelProperty(value="开始时间")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty(value="结束时间")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty(value="逻辑删除")
    @TableLogic
    @TableField("is_del")
    private Integer isDel;


}
