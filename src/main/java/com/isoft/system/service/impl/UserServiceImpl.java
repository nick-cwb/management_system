package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.User;
import com.isoft.system.entity.dto.PasswordDTO;
import com.isoft.system.entity.dto.UserDTO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.mapper.UserMapper;
import com.isoft.system.service.IEmpPositionService;
import com.isoft.system.service.IOrgEmpService;
import com.isoft.system.service.IUserService;
import com.isoft.system.utils.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DS("master")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static String USERNAME = "username";
    private static String PASSWORD = "password";

    @Override
    public IPage<User> findAll(UserDTO userDTO) {
        IPage<User> userPage = new Page<User>();
        //有机构id查询机构下的人
        if(userDTO.getOrgId()!=null && !"".equals(userDTO.getOrgId())){
            userPage.setRecords(baseMapper.findAllByOrgId(userDTO));
            userPage.setTotal(baseMapper.countPageSizeByOrgId(userDTO));
        }else if (userDTO.getOrgId()!=null && !"".equals(userDTO.getOrgId())){
            userPage.setRecords(baseMapper.findAllByPosiId(userDTO));
            userPage.setTotal(baseMapper.countPageSizeByPosiId(userDTO));
        } else {
            QueryWrapper<User> qw = new QueryWrapper<User>();
            if(userDTO.getName() != null && !"".equals(userDTO.getName())){
                qw.like(USERNAME,userDTO.getName());
            }
            userPage = baseMapper.selectPage(userDTO,qw);
        }
        return userPage;
    }

    @Override
    public boolean saveUser(User user) throws Exception {
        QueryWrapper<User> qw = new QueryWrapper<User>().eq(USERNAME,user.getName());
        User registUser = baseMapper.selectOne(qw);
        if (registUser != null){
            throw new BusinessException(BusinessExceptionEnum.ACCOUNT_EXIST.value());
        }
        user.setRegisterTime(Util.createTimestamp());
        int num = baseMapper.insert(user);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) throws Exception{
        int num = baseMapper.updateById(user);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updatePasswor(PasswordDTO passwordDTO) throws Exception {
        User user = baseMapper.selectById(passwordDTO.getUserId());
        if(!user.getPassword().equals(passwordDTO.getOldPassword())){
            throw new BusinessException(BusinessExceptionEnum.WRONG_OLD_PASSWORD.value());
        }
        User newUser = new User();
        newUser.setId(passwordDTO.getUserId());
        newUser.setPassword(passwordDTO.getNewPassword());
        int num = baseMapper.updateById(newUser);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }
}
