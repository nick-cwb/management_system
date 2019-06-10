package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.OrgEmp;
import com.isoft.system.entity.dto.OrgEmpResourceDTO;
import com.isoft.system.mapper.OrgEmpMapper;
import com.isoft.system.service.IOrgEmpService;
import com.isoft.system.utils.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@DS("master")
public class OrgEmpServiceImpl extends ServiceImpl<OrgEmpMapper, OrgEmp> implements IOrgEmpService {

	
    private static String EMP_ID = "emp_id";
    private static String ORG_ID = "org_id";
    private static String INSERT_SET = "insertSet";
    private static String DEL_SET = "delSet";

    @Override
    public Integer[]  findUserOrganization(Integer empId) {
    	List<Integer> orgIdList = new ArrayList<Integer>();
    	IPage<OrgEmp> orgEmpSearchPage = new Page<OrgEmp>();
        QueryWrapper<OrgEmp> qw = new QueryWrapper<OrgEmp>();
        qw.eq(EMP_ID, empId);
        IPage<OrgEmp> orgEmpPage = baseMapper.selectPage(orgEmpSearchPage,qw);
        for(OrgEmp orgEmp:orgEmpPage.getRecords()) {
        	orgIdList.add(orgEmp.getOrgId());
        }
        
        Integer[] result = new Integer[orgIdList.size()];
        if(orgIdList.size()>0) {
        	for(int i =0;i<orgIdList.size(); i++) {
        		result[i]=orgIdList.get(i);
        	}
        }
        
        return result;
    }
    
    @Override
    public boolean updateUserOrganization(OrgEmpResourceDTO orgEmpDTO) throws Exception{
        int num = 0;
        int insertNum = 0;
        int delNum = 0;
        
        Integer[]  orgEmpIdsDb = this.findUserOrganization(orgEmpDTO.getId());
        Set<Integer> savedSet = Util.arrayToSet(orgEmpIdsDb);
        Set<Integer> updateSet = Util.arrayToSet(orgEmpDTO.getOrganizationIds());
        Map<String,Set<Integer>> resultMap = Util.screenIdSet(savedSet, updateSet);
        
        Set<Integer> insertSet = resultMap.get(INSERT_SET);
        Set<Integer> delSet =  resultMap.get(DEL_SET);
        if(null != delSet) {
            for(Integer delId : delSet) {
           	 QueryWrapper<OrgEmp> qw = new QueryWrapper<OrgEmp>();
                qw.eq(ORG_ID, delId);
                int del = baseMapper.delete(qw);
                delNum = delNum+del;
           }
        }
        
        if(null != insertSet) {
            for(Integer insertId : insertSet) {
            	OrgEmp  orgEmp = new OrgEmp();
            	orgEmp.setEmpId(orgEmpDTO.getId());
            	orgEmp.setOrgId(insertId);
            	orgEmp.setAppId(0);
            	int insert = baseMapper.insert(orgEmp);
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
