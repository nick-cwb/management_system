package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("file")
public class LocalFile {

    @ApiModelProperty(value="文件id")
    @TableField("id")
    private int id;

    @ApiModelProperty(value="文件名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value="文件路径")
    @TableField("file_path")
    private String filePath;

    @ApiModelProperty(value="创建时间")
    @TableField("create_time")
    private Date createTime;
}
