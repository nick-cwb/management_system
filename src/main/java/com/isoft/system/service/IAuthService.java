package com.isoft.system.service;

import com.isoft.system.entity.Role;
import com.isoft.system.entity.dto.RoleResourceDTO;

import java.util.List;

public interface IAuthService {
    //根据用户id获取对应的用户菜单权限和按钮权限
    Object findUserAuth(Integer userId);

    //根据用户id获取对应的用户菜单权限和按钮权限
    Object findUserAuths(Integer userId);

    //根据用户id获取当前角色
    List<Role> findUserRole(Integer userId);

    //根据角色id查询角色响应资源权限
    Object findRoleAuth(Integer roleId);

    //修改编辑角色对应的资源权限
    Object updateRoleAuth(RoleResourceDTO resDTO) throws Exception;

}
