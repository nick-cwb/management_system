package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("vacation_days")
public class VacationDays {

    @ApiModelProperty(value="用户id")
    @TableId("user_id")
    private Integer userId;

    @ApiModelProperty(value="用户名称")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value="可休假天数")
    @TableField("remainder_days")
    private Integer remainderDays;

}
