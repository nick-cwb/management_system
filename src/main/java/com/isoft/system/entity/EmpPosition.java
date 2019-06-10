package com.isoft.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 *  员工岗位
 */
@Data
@TableName("emp_position")
public class EmpPosition {
	
	@TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("posi_id")
    private Integer posiId;
    @TableField("emp_id")
    private Integer empId;
    @TableField("app_id")
    private Integer appId;

}
