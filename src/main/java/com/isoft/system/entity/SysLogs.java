package com.isoft.system.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yanghu
 * @since 2019-01-11
 */
@Data
@TableName("sys_logs")
public class SysLogs extends Model<SysLogs> {

	private static final long serialVersionUID = 1L;
	@TableField("id")
	@TableId(type = IdType.AUTO)
    private int id;
	@TableField("userId")
	private int userId;
	@TableField("module")
	private String module;
	@TableField("flag")
	private Boolean flag;
	@TableField("remark")
	private String remark;
	@TableField("createTime")
	private Date createTime;
	@TableField("url")
	private String url;
	@TableField("request_body")
	private String requestBody;

	private String userName;
	
}
