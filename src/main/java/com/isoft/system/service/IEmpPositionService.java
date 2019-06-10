package com.isoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.EmpPosition;
import com.isoft.system.entity.dto.EmpPositionResourceDTO;


public interface IEmpPositionService extends IService<EmpPosition> {




    //根据用户id查询岗位
	Integer[] findUserPosition(Integer empId);
	
	//根据用户id编辑用户岗位
	boolean updateUserPosition(EmpPositionResourceDTO empPositionDTO) throws Exception;


}
