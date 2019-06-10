package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.SysLogs;
import com.isoft.system.entity.dto.SysLogsDTO;
import com.isoft.system.mapper.SysLogsMapper;
import com.isoft.system.service.ISysLogService;
import com.isoft.system.utils.Util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yanghu
 * @since 2019-01-11
 */
@Service
@DS("master")
public class SysLogServiceImpl extends ServiceImpl<SysLogsMapper, SysLogs> implements ISysLogService {


	@Override
	public void save(int userId, String module, Boolean flag, String remark) throws Exception {
		SysLogs sysLogs = new SysLogs();
		sysLogs.setUserId(userId);
		sysLogs.setFlag(flag);
		sysLogs.setModule(module);
		sysLogs.setRemark(remark);
		sysLogs.setCreateTime(Util.createTimestamp());
		baseMapper.insert(sysLogs);
		
	}

	@Override
	public boolean save(SysLogs sysLogs) {
		baseMapper.insert(sysLogs);
		return true;
	}
	
	

	@Override
	public IPage<SysLogs> findAll(SysLogsDTO sysLogsDTO) {
		/*Page<SysLogs> page = new Page<SysLogs>();
		List<SysLogs> sysLogsList = baseMapper.getSysLog(sysLogsDTO);
		page.setRecords(sysLogsList);*/
		long total = baseMapper.countSysLog(sysLogsDTO.getModule());
		List<SysLogs> list = baseMapper.getSysLog(sysLogsDTO);
		Page<SysLogs> page = new Page<SysLogs>().setTotal(total).setRecords(list);
		return page;
	}
	
	@Override
	public SysLogs getLogById(int id){
		return baseMapper.selectLogById(id);
	}
}
