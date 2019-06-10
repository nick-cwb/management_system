package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.PartyAuth;
import com.isoft.system.entity.dto.PartyAuthDTO;
import com.isoft.system.entity.dto.PartyAuthResourceDTO;
import com.isoft.system.mapper.PartyAuthMapper;
import com.isoft.system.service.IPartyAuthService;
import com.isoft.system.utils.Util;
import com.mysql.jdbc.StringUtils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@DS("master")
public class PartyAuthServiceImpl extends ServiceImpl<PartyAuthMapper, PartyAuth> implements IPartyAuthService {

	
    private static String PARTY_ID = "party_id";
    private static String PARTY_TYPE = "party_type";
    private static String ROLE_ID = "role_id";
    private static String ROLE_TYPE = "role";
    
    private static String INSERT_SET = "insertSet";
    private static String DEL_SET = "delSet";

    @Override
    public Integer[] findUserRole(PartyAuthDTO partyAuthDTO) {
    	List<Integer> roleIdList = new ArrayList<Integer>();
    	IPage<PartyAuth> partyAuthSearchPage = new Page<PartyAuth>();
        QueryWrapper<PartyAuth> qw = new QueryWrapper<PartyAuth>();
        if(!StringUtils.isNullOrEmpty(partyAuthDTO.getPartyType())) {
        	qw.eq(PARTY_TYPE, partyAuthDTO.getPartyType());
        }
        if(null != partyAuthDTO.getId()) {
        	 qw.eq(PARTY_ID, partyAuthDTO.getId());
        }
       
        IPage<PartyAuth> partyAuthPage = baseMapper.selectPage(partyAuthSearchPage,qw);
        for(PartyAuth partyAuth:partyAuthPage.getRecords()) {
        	roleIdList.add(partyAuth.getRoleId());
        }
        Integer[] result = new Integer[roleIdList.size()];
        if(roleIdList.size()>0) {
        	for(int i =0;i<roleIdList.size(); i++) {
        		result[i]=roleIdList.get(i);
        	}
        }
        return result;
    }
    
    
    @Override
    public boolean updateUserRole(PartyAuthResourceDTO resDTO) throws Exception{
        int num = 0;
        int insertNum = 0;
        int delNum = 0;
        
        PartyAuthDTO partyAuthDTO = new PartyAuthDTO();
        partyAuthDTO.setId(resDTO.getId());
        partyAuthDTO.setPartyType(resDTO.getPartyType());
        		
        Integer[]  roleIdsDb = this.findUserRole(partyAuthDTO);
        Set<Integer> savedSet = Util.arrayToSet(roleIdsDb);
        Set<Integer> updateSet = Util.arrayToSet(resDTO.getRoleIds());
        Map<String,Set<Integer>> resultMap = Util.screenIdSet(savedSet, updateSet);
        
        Set<Integer> insertSet = resultMap.get(INSERT_SET);
        Set<Integer> delSet =  resultMap.get(DEL_SET);
        if(null != delSet) {
        	 for(Integer delId : delSet) {
            	 QueryWrapper<PartyAuth> qw = new QueryWrapper<PartyAuth>();
                 qw.eq(ROLE_ID, delId);
                 qw.eq(PARTY_TYPE, resDTO.getPartyType());
                 qw.eq(PARTY_ID, resDTO.getId());
                 int del = baseMapper.delete(qw);
                 delNum = delNum+del;
            }
        }
        if(null != insertSet) {
        	for(Integer insertId : insertSet) {
               	
            	PartyAuth  partyAuth = new PartyAuth();
            	partyAuth.setPartyId(resDTO.getId());
            	partyAuth.setPartyType(resDTO.getPartyType());
            	partyAuth.setRoleId(insertId);
            	partyAuth.setRoleType(ROLE_TYPE);
            	partyAuth.setCreatedTime(Util.createTimestamp());
            	int insert = baseMapper.insert(partyAuth);
            	insertNum = insertNum+insert;
           }
        	
        }
       
        
        num = insertNum + delNum;
        if(num>0){
            return true;
        }else{
            return false;
        }
    }


}
