package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统应用
 */
@Data
@TableName("application")
public class Application {

    @ApiModelProperty(value="应用id")
    @TableField("id")
    private Integer id;

    @ApiModelProperty(value="应用名称")
    @TableField("app_name")
    private String appName;

    @ApiModelProperty(value="应用类型")
    @TableField("app_type")
    private String appType;

    @ApiModelProperty(value="应用描述")
    @TableField("app_desc")
    private String appDesc;

    @ApiModelProperty(value="访问地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value="访问协议")
    @TableField("protocal_type")
    private String protocalType;

    @ApiModelProperty(value="ip地址")
    @TableField("ipaddr")
    private String ipaddr;

    @ApiModelProperty(value="ip端口")
    @TableField("ipport")
    private String ipport;

    @ApiModelProperty(value="应用状态：0正常 1已关闭")
    @TableField("app_status")
    private Integer appStatus;

}
