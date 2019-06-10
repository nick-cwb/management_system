package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.isoft.system.entity.*;
import com.isoft.system.entity.dto.RoleResourceDTO;
import com.isoft.system.entity.vo.RoleResourceVO;
import com.isoft.system.entity.vo.UserAuthVO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.mapper.*;
import com.isoft.system.service.IAuthMenuService;
import com.isoft.system.service.IAuthService;
import com.isoft.system.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@DS("master")
public class AuthServiceImpl implements IAuthService {

    private static String ROLE_ID = "role_id";
    private static String EMPID = "emp_id";
    private static String MENU = "menu";
    private static String BUTTON = "btn";
    private static String INSERT_SET = "insertSet";
    private static String DEL_SET = "delSet";
    private static String PARTY_TYPE = "party_type";
    private static String PARTY_ID = "party_id";
    private static String EMP = "emp";
    private static String ORG = "org";
    private static String POSITION = "position";
    private static String RES_ID = "res_id";
    private static String RES_TYPE = "res_type";

    @Resource
    IAuthMenuService menuService;

    @Resource
    ResAuthMapper resAuthMapper;

    @Resource
    AuthMenuMapper menuMapper;

    @Resource
    AuthButtonMapper buttonMapper;

    @Resource
    PartyAuthMapper partyAuthMapper;

    @Resource
    OrgEmpMapper orgEmpMapper;

    @Resource
    EmpPositionMapper empPositionMapper;

    @Resource
    RoleMapper roleMapper;

    @Override
    public Object findUserAuth(Integer userId) {
        UserAuthVO userAuthVO = new UserAuthVO();
        Object menu = menuService.findMenuTree();
        String[] button = {"test_add","test_delete","test_update","test_list"};
        userAuthVO.setButton(button);
        userAuthVO.setMenu(menu);
        return userAuthVO;
    }

    @Override
    public Object findUserAuths(Integer userId) {
        //根据用户id获取该用户授权的全部角色
        List<Role> roleList = this.findUserRole(userId);
        //获取角色id集合
        Set<Integer> roleIds = new HashSet<Integer>();
        for (Role role:roleList) {
            roleIds.add(role.getId());
        }
        //根据角色id集合查询所有的资源权限
        List<ResAuth> resAuthList = resAuthMapper.selectList(new QueryWrapper<ResAuth>().in(ROLE_ID,roleIds));
        //遍历资源权限获取有权限的菜单id集合和按钮id集合
        Set<Integer> menuSet = new HashSet<Integer>();
        Set<Integer> btnSet = new HashSet<Integer>();
        for (ResAuth resAuth:resAuthList) {
            if(resAuth.getResType().equals(MENU)){
                menuSet.add(resAuth.getResId());
            }
            if(resAuth.getResType().equals(BUTTON)){
                btnSet.add(resAuth.getResId());
            }
        }
        //查询拥有权限的菜单集合和按钮集合
        List<AuthMenu> authMenus = new ArrayList<AuthMenu>();
        if(menuSet != null && menuSet.size()>0){
            authMenus = menuMapper.selectBatchIds(menuSet);
            Collections.sort(authMenus,Comparator.comparing(AuthMenu::getMenuOrder));
        }
        List<AuthButton> authButtons = new ArrayList<AuthButton>();
        if(btnSet != null && btnSet.size()>0){
            authButtons = buttonMapper.selectBatchIds(btnSet);
        }
        //获取菜单树
        AuthMenu authMenu = menuService.findMenuTree(authMenus);
        //获取按钮集合
        Set<String> btnStrs = new HashSet<String>();
        for (AuthButton btn:authButtons) {
            btnStrs.add(btn.getBtnCode());
        }
        String[] strs = btnStrs.toArray(new String[btnStrs.size()]);
        UserAuthVO userAuthVO = new UserAuthVO();
        userAuthVO.setButton(strs);
        userAuthVO.setMenu(authMenu);
        return userAuthVO;
    }

    @Override
    public List<Role> findUserRole(Integer userId) {
        //根据用户id查询到用户所在岗位
        List<EmpPosition> empPositionList = empPositionMapper.selectList(new QueryWrapper<EmpPosition>().eq(EMPID,userId));
        //获取用户的岗位id
        Set<Integer> posiIds = new HashSet<Integer>();
        for (EmpPosition empPosi:empPositionList) {
            posiIds.add(empPosi.getPosiId());
        }
        //根据用户所在机构查询机构拥有的角色
        List<OrgEmp> orgEmpList = orgEmpMapper.selectList(new QueryWrapper<OrgEmp>().eq(EMPID,userId));
        //获取用户的机构id
        Set<Integer> orgIds = new HashSet<Integer>();
        for (OrgEmp rogEmp:orgEmpList) {
            orgIds.add(rogEmp.getOrgId());
        }
        //查询用户的角色
        List<PartyAuth> userAuthList = partyAuthMapper.selectList(new QueryWrapper<PartyAuth>().eq(PARTY_TYPE,EMP).eq(PARTY_ID,userId));
        List<PartyAuth> orgAuthList = new ArrayList<PartyAuth>();
        if(orgIds.size()>0){
            orgAuthList = partyAuthMapper.selectList(new QueryWrapper<PartyAuth>().eq(PARTY_TYPE,ORG).in(PARTY_ID,orgIds));
        }
        List<PartyAuth> positionAuthList = new ArrayList<PartyAuth>();
        if(posiIds.size()>0){
            positionAuthList = partyAuthMapper.selectList(new QueryWrapper<PartyAuth>().eq(PARTY_TYPE,POSITION).in(PARTY_ID,posiIds));
        }
        //获取筛重获取到该用户的全部角色id
        Set<Integer> roleIds = new HashSet<Integer>();
        screenId(roleIds,userAuthList);
        screenId(roleIds,orgAuthList);
        screenId(roleIds,positionAuthList);
        //根据角色id获取角色列表
        List<Role> roleList = roleMapper.selectBatchIds(roleIds);
        return roleList;
    }

    @Override
    public Object findRoleAuth(Integer roleId) {
        //根据角色id获取资源权限对象
        List<ResAuth> resAuths = resAuthMapper.selectList(new QueryWrapper<ResAuth>().eq(ROLE_ID,roleId));
        if (resAuths == null || resAuths.size() == 0){
            throw new BusinessException(BusinessExceptionEnum.NULL_RESOURCE_AUTHORIZED.value());
        }
        Map<String,Set<Integer>> map = findAuthByRoleId(roleId,resAuths);
        RoleResourceVO roleResourceVO = new RoleResourceVO();
        List<AuthMenu> menuList = new ArrayList<AuthMenu>();
        if(map.get(MENU) != null && map.get(MENU).size()>0){
            menuList = menuMapper.selectBatchIds(map.get(MENU));
            roleResourceVO.setMenuList(menuList);
        }
        List<AuthButton> buttonList = new ArrayList<AuthButton>();
        if(map.get(BUTTON) != null && map.get(BUTTON).size()>0){
            buttonList = buttonMapper.selectBatchIds(map.get(BUTTON));
            roleResourceVO.setButtonList(buttonList);
        }
        return roleResourceVO;
    }

    @Override
    @Transactional
    public Object updateRoleAuth(RoleResourceDTO resDTO) throws Exception {
        boolean flag = true;
        //根据角色id获取资源权限对象
        List<ResAuth> resAuths = resAuthMapper.selectList(new QueryWrapper<ResAuth>().eq(ROLE_ID,resDTO.getRoleId()));
        Set<Integer> savedMenu = new HashSet<Integer>();
        Set<Integer> savedButton = new HashSet<Integer>();
        if (resAuths.size()>0){
            //获取已保存的菜单按钮资源id集合
            Map<String,Set<Integer>> map = findAuthByRoleId(resDTO.getRoleId(),resAuths);
            savedButton = map.get(BUTTON);
            savedMenu = map.get(MENU);
        }
        //更新角色的菜单权限
        Map<String,Set<Integer>> menuMap = Util.screenIdSet(savedMenu,Util.arrayToSet(resDTO.getMenuIds()));
        Set<Integer> menuInsert = menuMap.get(INSERT_SET) != null ? menuMap.get(INSERT_SET) : null;
        Set<Integer> menuDel = menuMap.get(DEL_SET) != null ? menuMap.get(DEL_SET) : null;
        if(menuDel != null && menuDel.size()>0){
            for (Integer i:menuDel) {
                int num = resAuthMapper.delete(new QueryWrapper<ResAuth>().eq(RES_ID,i).eq(ROLE_ID,resDTO.getRoleId()).eq(RES_TYPE,MENU));
            }
        }
        if(menuInsert != null && menuInsert.size()>0){
            for (Integer i:menuInsert) {
                int num = resAuthMapper.insert(assembleEntity(resDTO.getRoleId(),i,MENU,resDTO.getUserId()));
            }
        }
        //更新角色的按钮权限
        Map<String,Set<Integer>> btnMap = Util.screenIdSet(savedButton,Util.arrayToSet(resDTO.getButtonIds()));
        Set<Integer> btnInsert = btnMap.get(INSERT_SET) != null ? btnMap.get(INSERT_SET) : null;
        Set<Integer> btnDel = btnMap.get(DEL_SET) != null ? btnMap.get(DEL_SET) : null;
        if(btnDel != null && btnDel.size()>0){
            for (Integer i:btnDel) {
                int num = resAuthMapper.delete(new QueryWrapper<ResAuth>().eq(RES_ID,i).eq(ROLE_ID,resDTO.getRoleId()).eq(RES_TYPE,BUTTON));
            }
        }
        if(btnInsert != null && btnInsert.size()>0){
            for (Integer i:btnInsert) {
                int num = resAuthMapper.insert(assembleEntity(resDTO.getRoleId(),i,BUTTON,resDTO.getUserId()));
            }
        }
        return true;
    }

    /**
     * 根据资源类型获取按钮、菜单的id集合
     * @param roleId
     * @param resAuths
     * @return
     */
    private static Map<String,Set<Integer>> findAuthByRoleId(Integer roleId,List<ResAuth> resAuths){
        Set<Integer> menuIds = new HashSet<Integer>();
        Set<Integer> buttonIds = new HashSet<Integer>();
        for (ResAuth resAuth : resAuths) {
            if (resAuth.getResType().equals(MENU)){
                menuIds.add(resAuth.getResId());
                continue;
            }
            if (resAuth.getResType().equals(BUTTON)){
                buttonIds.add(resAuth.getResId());
                continue;
            }
        }
        Map<String,Set<Integer>> map = new HashMap<String, Set<Integer>>();
        map.put(MENU,menuIds);
        map.put(BUTTON,buttonIds);
        return map;
    }


    private static ResAuth assembleEntity(Integer roleId,Integer resId,String resType,Integer userId) throws Exception {
        ResAuth resAuth = new ResAuth();
        resAuth.setRoleId(roleId);
        resAuth.setResId(resId);
        resAuth.setResType(resType);
        resAuth.setCreatedTime(Util.createTimestamp());
        resAuth.setCreatedEmp(userId);
        return resAuth;
    }


    private static void screenId(Set<Integer> ids,List<PartyAuth> partyAuthList){
        if(partyAuthList.size() == 0){
            return;
        }
        for (PartyAuth partyAuth : partyAuthList){
            ids.add(partyAuth.getRoleId());
        }
    }

}
