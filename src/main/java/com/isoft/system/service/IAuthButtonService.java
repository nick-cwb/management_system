package com.isoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.AuthButton;
import com.isoft.system.entity.dto.AuthButtonDTO;
import com.isoft.system.entity.dto.ButtonDTO;

import java.util.List;

public interface IAuthButtonService extends IService<AuthButton> {

    //查询按钮分页
    IPage<AuthButton> findAll(AuthButtonDTO authButtonDTO);

    //根据菜单编码获取下面的全部按钮
    List<AuthButton> findAllMenu(ButtonDTO buttonDTO);

    //新增按钮
    boolean saveButton(AuthButton authButton);

    //修改按钮
    boolean updateButton(AuthButton authButton);

}
