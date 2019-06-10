package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.User;
import com.isoft.system.entity.vo.LoginVO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.enums.NumberEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.mapper.UserMapper;
import com.isoft.system.service.ILoginService;
import com.isoft.system.utils.JwtHelper;
import org.springframework.stereotype.Service;

@Service
@DS("master")
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements ILoginService {

    private static String USERNAME = "username";
    private static String PASSWORD = "password";

    /**
     * 用户登录，返回有三种情况：
     * 1、账号密码正确返回token; 2、账号密码正确但账号冻结返回冻结; 3、账号密码错误
     * @param user
     * @return
     */
    @Override
    public Object signToken(User user){
        QueryWrapper<User> qw = new QueryWrapper<User>().
                eq(USERNAME,user.getName()).eq(PASSWORD,user.getPassword());
        User loginUser = baseMapper.selectOne(qw);
        //用户名密码错误
        if (loginUser == null){
            throw new BusinessException(BusinessExceptionEnum.WRONG_PASSWORD.value());
        }

        if (loginUser.getUserStatus() == NumberEnum.ONE.key()){
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_FREEZE.value());
        }
        String userId = loginUser.getId()+"";
        String token = JwtHelper.sign(userId,user.getPassword());

        return new LoginVO(token,loginUser);
    }

    /**
     * 根据token返回用户
     * @param token
     * @return
     */
    @Override
    public User findUserByToken(String token) {
        //String tokens = token.substring(7);
        Integer userId = Integer.valueOf(JwtHelper.getUserId(token));
        User user = baseMapper.selectById(userId);
        return user;
    }


}
