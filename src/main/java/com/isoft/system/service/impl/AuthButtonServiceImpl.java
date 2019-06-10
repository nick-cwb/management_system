package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.AuthButton;
import com.isoft.system.entity.AuthMenu;
import com.isoft.system.entity.ResAuth;
import com.isoft.system.entity.dto.AuthButtonDTO;
import com.isoft.system.entity.dto.ButtonDTO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.mapper.AuthButtonMapper;
import com.isoft.system.mapper.AuthMenuMapper;
import com.isoft.system.mapper.ResAuthMapper;
import com.isoft.system.service.IAuthButtonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@DS("master")
public class AuthButtonServiceImpl extends ServiceImpl<AuthButtonMapper,AuthButton> implements IAuthButtonService {

    private static String BTN_NAME = "btn_name";
    private static String BTN_LABEL = "btn_label";
    private static String BTN_CODE = "btn_code";
    private static String MENU_CODE = "menu_code";
    private static String RES_TYPE = "res_type";
    private static String ROLE_ID = "role_id";
    private static String RES_ID = "res_id";
    private static String MENU = "menu";
    private static String BUTTON = "btn";


    @Resource
    private AuthMenuMapper authMenuMapper;

    @Resource
    private ResAuthMapper resAuthMapper;

    @Override
    public IPage<AuthButton> findAll(AuthButtonDTO authButtonDTO) {
        QueryWrapper<AuthButton> qw = new QueryWrapper<AuthButton>();
        if(authButtonDTO.getBtnCode() != null && !"".equals(authButtonDTO.getBtnCode())){
            qw.like(BTN_CODE,authButtonDTO.getBtnCode());
        }
        if(authButtonDTO.getBtnLabel() != null && !"".equals(authButtonDTO.getBtnLabel())){
            qw.like(BTN_LABEL,authButtonDTO.getBtnLabel());
        }
        if(authButtonDTO.getBtnName() != null && !"".equals(authButtonDTO.getBtnName())){
            qw.like(BTN_NAME,authButtonDTO.getBtnName());
        }
        if(authButtonDTO.getMenuCode() != null && !"".equals(authButtonDTO.getMenuCode())){
            qw.like(MENU_CODE,authButtonDTO.getMenuCode());
        }
        IPage<AuthButton> authButtonPage = baseMapper.selectPage(authButtonDTO,qw);
        return authButtonPage;
    }

    @Override
    public List<AuthButton> findAllMenu(ButtonDTO buttonDTO) {
        List<AuthButton> authButtonList = new ArrayList<AuthButton>();
        //如果按钮编码为空，抛出异常
        if(buttonDTO.getMenuCode() == null || "".equals(buttonDTO.getMenuCode())){
            throw new BusinessException(BusinessExceptionEnum.NULL_MENU_CODE.value());
        }
        //如果角色id为空，直接返回查到的按钮集合
        authButtonList = baseMapper.selectList(new QueryWrapper<AuthButton>().eq(MENU_CODE,buttonDTO.getMenuCode()));
        if(buttonDTO.getRoleId() == null){
            return authButtonList;
        }
        //角色id不为空,根据查询到的角色返回拥有的该菜单下的按钮权限
        //根据菜单编码查询菜单
        AuthMenu authMenu = authMenuMapper.selectOne(new QueryWrapper<AuthMenu>().eq(MENU_CODE,buttonDTO.getMenuCode()));
        if(authMenu == null){
            throw new BusinessException(BusinessExceptionEnum.WRONG_MENU_CODE.value());
        }
        //查询该菜单编码对应的按钮
        List<ResAuth> resAuthList = resAuthMapper.selectList(new QueryWrapper<ResAuth>().eq(RES_TYPE,BUTTON).eq(ROLE_ID,buttonDTO.getRoleId()));
        List<AuthButton> btnList = new ArrayList<AuthButton>();
        for (AuthButton authButton:authButtonList) {
            for (ResAuth resAuth:resAuthList) {
                if (resAuth.getResId() == authButton.getId()){
                    btnList.add(authButton);
                    break;
                }
            }
        }
        return btnList;
    }

    @Override
    public boolean saveButton(AuthButton authButton) {
        //保存前先查询buttoncode是否存在
        AuthButton registButton = baseMapper.selectOne(new QueryWrapper<AuthButton>().eq(BTN_CODE,authButton.getBtnCode()));
        if(registButton != null){
            throw new BusinessException(BusinessExceptionEnum.BUTTON_EXIST.value());
        }
        int num = baseMapper.insert(authButton);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateButton(AuthButton authButton) {
        int num = baseMapper.updateById(authButton);
        if(num>0){
            return true;
        }else{
            return false;
        }
    }
}
