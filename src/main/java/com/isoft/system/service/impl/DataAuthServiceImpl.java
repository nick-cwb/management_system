package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.DataAuth;
import com.isoft.system.entity.dto.DataAuthDTO;
import com.isoft.system.mapper.DataAuthMapper;
import com.isoft.system.service.IDataAuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("master")
public class DataAuthServiceImpl extends ServiceImpl<DataAuthMapper,DataAuth> implements IDataAuthService {
    @Override
    public DataAuth findDataAuthByID(Integer menuId, Integer roleId) {
        QueryWrapper<DataAuth> qw = new QueryWrapper<DataAuth>();
        qw.eq("menu_id",menuId);
        qw.eq("role_id",roleId);
        DataAuth dataAuth = baseMapper.selectOne(qw);
        return dataAuth;
    }

    @Override
    public boolean updDataAuth(DataAuth dataAuth) {
        QueryWrapper<DataAuth> qw = new QueryWrapper<DataAuth>();
        qw.eq("menu_id",dataAuth.getMenuId());
        qw.eq("role_id",dataAuth.getRoleId());
        int num = baseMapper.update(dataAuth,qw);
        if (num>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean delDataAuth(Integer menuId, Integer roleId) {
        QueryWrapper<DataAuth> qw = new QueryWrapper<DataAuth>();
        qw.eq("menu_id",menuId);
        qw.eq("role_id",roleId);
        int num = baseMapper.delete(qw);
        if (num>0){
            return true;
        }else {
            return false;
        }    }

    @Override
    public IPage<DataAuth> findAll(DataAuthDTO dataAuthDTO) {
        List<DataAuth> list = baseMapper.findAllData(dataAuthDTO);
        long countNum = baseMapper.countAll(dataAuthDTO);
        Page<DataAuth> page = new Page<DataAuth>().setRecords(list).setTotal(countNum);
        return page;
    }
}
