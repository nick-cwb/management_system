package com.isoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.PartyAuth;
import com.isoft.system.entity.dto.PartyAuthDTO;
import com.isoft.system.entity.dto.PartyAuthResourceDTO;


public interface IPartyAuthService extends IService<PartyAuth> {

    //根据用户id获取用户角色
	Integer[] findUserRole(PartyAuthDTO  partyAuthDTO);
	
	//根据用户id获编辑用户角色
	boolean updateUserRole(PartyAuthResourceDTO resDTO) throws Exception;

}
