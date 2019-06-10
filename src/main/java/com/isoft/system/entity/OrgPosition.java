package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 机构岗位
 */

@Data
@TableName("org_position")
public class OrgPosition {

    @ApiModelProperty(value="机构id")
    @TableField("org_id")
    private Integer orgId;

    @ApiModelProperty(value="岗位id")
    @TableField("position_id")
    private Integer positionId;

}
