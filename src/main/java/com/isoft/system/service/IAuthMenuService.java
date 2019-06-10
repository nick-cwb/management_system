package com.isoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.AuthMenu;
import com.isoft.system.entity.dto.AuthMenuDTO;

import java.util.List;

public interface IAuthMenuService extends IService<AuthMenu> {

    //分页查询所有菜单
    IPage<AuthMenu> findAll(AuthMenuDTO authMenuDTO);

    //数据权限
    List<AuthMenu> findAllAuth(Integer roleId,Integer menuId);

    //查询树形菜单
    Object findMenuTree();

    //查询树形菜单
    AuthMenu findMenuTree(List<AuthMenu> menus);

    //新增菜单
    boolean saveAuthMenu(AuthMenu authMenu) throws Exception;

    //修改菜单
    boolean updateAuthMenu(AuthMenu authMenu) throws Exception;

}
