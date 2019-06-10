package com.isoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.DataAuth;
import com.isoft.system.entity.dto.DataAuthDTO;

public interface IDataAuthService extends IService<DataAuth> {

    //根据菜单id和角色id查询数据权限记录
    DataAuth findDataAuthByID(Integer menuId,Integer roleId);

    //新增数据权限


    //修改数据权限
    boolean updDataAuth(DataAuth dataAuth);

    //删除数据权限
    boolean delDataAuth(Integer menuId,Integer roleId);

    //分页查询数据权限
    IPage<DataAuth> findAll(DataAuthDTO dataAuthDTO);

}
