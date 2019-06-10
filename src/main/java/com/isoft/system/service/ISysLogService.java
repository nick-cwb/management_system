package com.isoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.SysLogs;
import com.isoft.system.entity.dto.SysLogsDTO;

/**
 * <p>
 *  服务类
 * </p>
 * @author Yanghu
 * @since 2019-01-11
 */
public interface ISysLogService extends IService<SysLogs> {
	
	public void save(int userId, String module, Boolean flag, String remark) throws Exception;
	
	@Override
	public boolean save(SysLogs sysLogs);
	
    //查询日志
	public IPage<SysLogs> findAll(SysLogsDTO sysLogsDTO);
	
	
	public SysLogs getLogById(int id);
}
