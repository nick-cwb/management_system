package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("multi_data")
public class MultiData {

    @ApiModelProperty(value="多数据源测试")
    @TableField("name")
    private String name;

}
