package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.Role;
import com.isoft.system.entity.dto.RoleDTO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.mapper.RoleMapper;
import com.isoft.system.service.IRoleService;
import com.isoft.system.utils.Util;
import org.springframework.stereotype.Service;

@Service
@DS("master")
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements IRoleService {

    private static String ROLE_CODE = "role_code";
    private static String ROLE_NAME = "role_name";


    @Override
    public IPage<Role> findAll(RoleDTO roleDTO) {
        QueryWrapper<Role> qw = new QueryWrapper<Role>();
        if(roleDTO.getRoleCode() != null && !"".equals(roleDTO.getRoleCode())){
            qw.like(ROLE_CODE,roleDTO.getRoleCode());
        }
        if(roleDTO.getRoleName() != null && !"".equals(roleDTO.getRoleName())){
            qw.like(ROLE_NAME,roleDTO.getRoleName());
        }
        IPage<Role> rolePage = baseMapper.selectPage(roleDTO,qw);
        return rolePage;
    }

    @Override
    public boolean saveRole(Role role) throws Exception{
        //查询rolecode是否已有值
        Role registRole = baseMapper.selectOne(new QueryWrapper<Role>().eq(ROLE_CODE,role.getRoleCode()));
        //若存在抛出异常
        if(registRole != null){
            throw new BusinessException(BusinessExceptionEnum.ROLE_EXIST.value());
        }
        boolean flag = false;
        role.setCreatedAt(Util.createTimestamp());
        int num = baseMapper.insert(role);
        if(num>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateRole(Role role) {
        boolean flag = false;
        int num = baseMapper.updateById(role);
        if(num>0){
            return true;
        }else {
            return false;
        }
    }
}
