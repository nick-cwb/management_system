package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.AuthMenu;
import com.isoft.system.entity.DataAuth;
import com.isoft.system.entity.dto.AuthMenuDTO;
import com.isoft.system.enums.BusinessExceptionEnum;
import com.isoft.system.exception.BusinessException;
import com.isoft.system.mapper.AuthMenuMapper;
import com.isoft.system.mapper.DataAuthMapper;
import com.isoft.system.service.IAuthMenuService;
import com.isoft.system.utils.Util;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@DS("master")
public class AuthMenuServiceImpl extends ServiceImpl<AuthMenuMapper,AuthMenu> implements IAuthMenuService {

    //0为菜单根目录
    private static Integer ROOT_ID = 1;

    private static String MENU_NAME = "menu_name";
    private static String MENU_LABEL = "menu_label";
    private static String MENU_CODE = "menu_code";


    @Resource
    DataAuthMapper dataAuthMapper;

    @Override
    public IPage<AuthMenu> findAll(AuthMenuDTO authMenuDTO) {
        QueryWrapper<AuthMenu> qw = new QueryWrapper<AuthMenu>();
        if(authMenuDTO.getMenuCode() != null && !"".equals(authMenuDTO.getMenuCode())){
            qw.like(MENU_CODE,authMenuDTO.getMenuCode());
        }
        if(authMenuDTO.getMenuLabel() != null && !"".equals(authMenuDTO.getMenuLabel())){
            qw.like(MENU_LABEL,authMenuDTO.getMenuLabel());
        }
        if(authMenuDTO.getMenuName() != null && !"".equals(authMenuDTO.getMenuName())){
            qw.like(MENU_NAME,authMenuDTO.getMenuName());
        }
        IPage<AuthMenu> authMenuPage = baseMapper.selectPage(authMenuDTO,qw);
        return authMenuPage;
    }

    @Override
    public List<AuthMenu> findAllAuth(Integer roleId,Integer muneId) {
        //根据menuId和roleID获取数据权限条件
        DataAuth dataAuth = dataAuthMapper.selectOne(new QueryWrapper<DataAuth>().eq("menu_id",muneId).eq("role_id",roleId));
        JSONArray jsonArray = JSONArray.fromObject(dataAuth.getContent());
        QueryWrapper<AuthMenu> qw = new QueryWrapper<AuthMenu>();
        for (int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String operate = (String) jsonObject.get("operate");
            String fileName = (String) jsonObject.get("fileName");
            String content = (String) jsonObject.get("content");
            if(operate.equals("eq")){
                qw.eq(fileName,content);
            }
            if(operate.equals("like")){
                qw.like(fileName,content);
            }
            if(operate.equals("in")){
                qw.in(fileName,Util.transformStringToSet(content));
            }
        }
        List<AuthMenu> list = baseMapper.selectList(qw);
        return list;
    }

    /**
     * 查询树形菜单
     * @return
     */
    @Override
    public Object findMenuTree() {
        //查询全部菜单
        List<AuthMenu> menus = baseMapper.selectList(null);
        //调用递归算法生成菜单
        AuthMenu menu = getMenuTree(ROOT_ID,menus);
        return menu;
    }

    @Override
    public AuthMenu findMenuTree(List<AuthMenu> menus) {
        return getMenuTree(ROOT_ID,menus);
    }

    @Override
    public boolean saveAuthMenu(AuthMenu authMenu) throws Exception {
        //添加前校验menucode是否已存在
        AuthMenu registMenu = baseMapper.selectOne(new QueryWrapper<AuthMenu>().eq(MENU_CODE,authMenu.getMenuCode()));
        if (registMenu != null){
            throw new BusinessException(BusinessExceptionEnum.MENU_EXIST.value());
        }
        boolean flag = false;
        int num = baseMapper.insert(authMenu);
        if(num>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateAuthMenu(AuthMenu authMenu) throws Exception {
        boolean flag = false;
        int num = baseMapper.updateById(authMenu);
        if(num>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 递归算法解析生成树形结构
     * @return
     */
    private static AuthMenu getMenuTree(Integer rootId,List<AuthMenu> menuList){
        //根据id获取根节点对象
        AuthMenu node = getRootMenu(rootId,menuList);
        //查询根下的所有子节点
        List<AuthMenu> childTreeNodes = getChildMenu(rootId,menuList);
        //遍历子节点
        for(AuthMenu child : childTreeNodes){
            AuthMenu n = getMenuTree(child.getId(),menuList); //递归
            if(node.getChildren() == null){
                node.setChildren(new ArrayList<AuthMenu>());
            }
            node.getChildren().add(n);
        }
        return node;
    }

    /**
     * 获取根节点
     * @param rootId
     * @param menuList
     * @return
     */
    private static AuthMenu getRootMenu(Integer rootId,List<AuthMenu> menuList){
        for (AuthMenu menu:menuList) {
            if(((Integer)menu.getId()).intValue() == rootId.intValue()){
                return menu;
            }
        }
        return null;
    }

    /**
     * 获取子节点
     */
    private static List<AuthMenu> getChildMenu(Integer parentId,List<AuthMenu> menuList){
        List<AuthMenu> menus = new LinkedList<AuthMenu>();
        for (AuthMenu menu:menuList) {
            if(menu.getParentId().intValue() == parentId.intValue()){
                menus.add(menu);
            }
        }
        return menus;
    }

}
