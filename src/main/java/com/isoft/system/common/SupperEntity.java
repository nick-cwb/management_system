package com.isoft.system.common;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SupperEntity {

    @ApiModelProperty(value="Id")
    @TableField("ID")
    public int id;


    @ApiModelProperty(value="创建时间")
    @TableField("CREATE_TIME")
    public Date createTime;


    @ApiModelProperty(value="创建人")
    @TableField("CREATOR")
    public int creator;


    @ApiModelProperty(value="更新时间")
    @TableField("UPDATE_TIME")
    public Date updateTime;


    @ApiModelProperty(value="修改人")
    @TableField("UPDATER")
    public int updater;

}
