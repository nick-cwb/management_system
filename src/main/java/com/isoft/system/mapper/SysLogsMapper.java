package com.isoft.system.mapper;

import java.util.List;

import com.isoft.system.common.SupperMapper;
import com.isoft.system.entity.SysLogs;
import com.isoft.system.entity.dto.SysLogsDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yanghu
 * @since 2019-01-11
 */
public interface SysLogsMapper extends SupperMapper<SysLogs> {

	@Insert("insert into sys_logs(userId, module, flag, remark, createTime, url, request_body) values(#{userId}, #{module}, #{flag}, #{remark}, #{createTime} #{url}, #{requestBody})")
	int save(SysLogs sysLogs);
	
	@Select("SELECT sl.id as id, sl.userId as userId, " + 
			" sl.module as module, sl.flag as flag, " + 
			" sl.remark as remark, " + 
			" sl.createTime as createTime, " +
			" sl.url as url " +
			" sl.request_body as requestBody" +
			" (SELECT u.username FROM user u WHERE u.id=sl.userId)  as userName " + 
			" FROM sys_logs sl WHERE sl.id= #{id} ")
	SysLogs selectLogById(@Param("id") int id);


	@Select("SELECT COUNT(1) FROM sys_logs sl " +
			"LEFT JOIN `user` u ON sl.userId = u.id WHERE module LIKE #{module} ")
	long countSysLog(String module);


	@Select("SELECT sl.id as id, sl.userId as userId, " + 
			" sl.module as module, sl.flag as flag, " + 
			" sl.remark as remark, " +
			" sl.url as url ," +
			" sl.request_body as requestBody ," +
			" sl.createTime as createTime, " +
			" u.username as userName " +
			" FROM sys_logs sl " +
			" LEFT JOIN `user` u " +
			" ON sl.userId = u.id " +
			" WHERE module LIKE #{module} " +
			" ORDER BY createTime DESC")
    List<SysLogs> getSysLog(Page<SysLogs> page);
}
