package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("base_data_option")
public class BaseDataOption {

    @ApiModelProperty(value="主键")
    @TableField("id")
    private int id;

    @ApiModelProperty(value="基础数据名称")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value="基础数据选项名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value="基础数据编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value="排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value="逻辑删除")
    @TableLogic
    @TableField("is_del")
    private Integer isDel;

}
