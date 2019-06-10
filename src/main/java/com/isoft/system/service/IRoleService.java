package com.isoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.Role;
import com.isoft.system.entity.dto.RoleDTO;

public interface IRoleService extends IService<Role> {

    //查询角色权限
    IPage<Role> findAll(RoleDTO roleDTO);

    //新增角色
    boolean saveRole(Role role) throws Exception;

    //修改角色
    boolean updateRole(Role role);

}
