package com.isoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.User;

public interface ILoginService extends IService<User> {

    //首次登陆签发token
    public Object signToken(User user);

    //根据token获取用户信息
    User findUserByToken(String token);

}
