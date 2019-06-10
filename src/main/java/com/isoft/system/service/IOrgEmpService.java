package com.isoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.OrgEmp;
import com.isoft.system.entity.dto.OrgEmpResourceDTO;


public interface IOrgEmpService extends IService<OrgEmp> {

    //根据用户id查询机构
	Integer[] findUserOrganization(Integer empId);
	
	//根据用户id编辑用户机构
	boolean updateUserOrganization(OrgEmpResourceDTO orgEmpDTO) throws Exception;


}
