package com.isoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.User;
import com.isoft.system.entity.dto.PasswordDTO;
import com.isoft.system.entity.dto.UserDTO;

public interface IUserService extends IService<User> {

    //查询所有用户
    IPage<User> findAll(UserDTO userDTO);

    //新增用户
    boolean saveUser(User user) throws Exception;

    //修改用户
    boolean updateUser(User user) throws Exception;

    //修改密码
    boolean updatePasswor(PasswordDTO passwordDTO) throws Exception;

}
