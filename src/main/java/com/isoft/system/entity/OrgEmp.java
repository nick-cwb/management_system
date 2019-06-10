package com.isoft.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 员工机构
 */
@Data
@TableName("org_emp")
public class OrgEmp {

    @ApiModelProperty(value="主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value="机构id")
    @TableField("org_id")
    private Integer orgId;

    @ApiModelProperty(value="员工id")
    @TableField("emp_id")
    private Integer empId;

    @ApiModelProperty(value="所属应用id")
    @TableField("app_id")
    private Integer appId;

}
