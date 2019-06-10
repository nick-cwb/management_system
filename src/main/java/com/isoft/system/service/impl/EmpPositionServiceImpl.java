package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.EmpPosition;
import com.isoft.system.entity.dto.EmpPositionResourceDTO;
import com.isoft.system.mapper.EmpPositionMapper;
import com.isoft.system.service.IEmpPositionService;
import com.isoft.system.utils.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@DS("master")
public class EmpPositionServiceImpl extends ServiceImpl<EmpPositionMapper, EmpPosition> implements IEmpPositionService {

	
    private static String EMP_ID = "emp_id";
    private static String POSI_ID = "posi_id";
    private static String INSERT_SET = "insertSet";
    private static String DEL_SET = "delSet";

    @Override
    public Integer[]  findUserPosition(Integer empId) {
    	List<Integer> positionIdList = new ArrayList<Integer>();
    	IPage<EmpPosition> empPositionSearchPage = new Page<EmpPosition>();
        QueryWrapper<EmpPosition> qw = new QueryWrapper<EmpPosition>();
        qw.eq(EMP_ID, empId);
        IPage<EmpPosition> empPositionPage = baseMapper.selectPage(empPositionSearchPage,qw);
        for(EmpPosition empPosition:empPositionPage.getRecords()) {
        	positionIdList.add(empPosition.getPosiId());
        }
        Integer[] result = new Integer[positionIdList.size()];
        if(positionIdList.size()>0) {
        	for(int i =0;i<positionIdList.size(); i++) {
        		result[i]=positionIdList.get(i);
        	}
        }
        return result;
    }
    
    
    @Override
    public boolean updateUserPosition(EmpPositionResourceDTO empPositionDTO) throws Exception{
        int num = 0;
        int insertNum = 0;
        int delNum = 0;
        
        Integer[]  positionIdsDb = this.findUserPosition(empPositionDTO.getId());
        Set<Integer> savedSet = Util.arrayToSet(positionIdsDb);
        Set<Integer> updateSet = Util.arrayToSet(empPositionDTO.getPositionIds());
        Map<String,Set<Integer>> resultMap = Util.screenIdSet(savedSet, updateSet);
        
        Set<Integer> insertSet = resultMap.get(INSERT_SET);
        Set<Integer> delSet =  resultMap.get(DEL_SET);
        if(null != delSet){
            for(Integer delId : delSet) {
           	 QueryWrapper<EmpPosition> qw = new QueryWrapper<EmpPosition>();
                qw.eq(POSI_ID, delId);
                int del = baseMapper.delete(qw);
                delNum = delNum+del;
           }
        }
        
        if(null != insertSet) {
        	  for(Integer insertId : insertSet) {
              	EmpPosition  empPosition = new EmpPosition();
              	empPosition.setEmpId(empPositionDTO.getId());
              	empPosition.setPosiId(insertId);
              	empPosition.setAppId(0);
              	int insert = baseMapper.insert(empPosition);
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
