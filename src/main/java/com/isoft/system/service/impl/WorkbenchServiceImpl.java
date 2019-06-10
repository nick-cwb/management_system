package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.AuthMenu;
import com.isoft.system.entity.Workbench;
import com.isoft.system.entity.dto.WorkbenchDTO;
import com.isoft.system.mapper.AuthMenuMapper;
import com.isoft.system.mapper.WorkbenchMapper;
import com.isoft.system.service.IWorkbenchService;
import com.isoft.system.utils.Util;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@DS("master")
public class WorkbenchServiceImpl extends ServiceImpl<WorkbenchMapper,Workbench> implements IWorkbenchService {

    private static String USER_ID = "user_id";
    private static String MENU_ID = "menu_id";
    private static String INSERT_SET = "insertSet";
    private static String DEL_SET = "delSet";

    @Resource
    AuthMenuMapper menuMapper;

    @Override
    public Object updateWorkbench(WorkbenchDTO workbenchDTO) throws Exception {
        Set<Integer> savedSet = new HashSet<Integer>();
        //根据用户id查询已经保存的工作台页面
        List<Workbench> list = baseMapper.selectList(new QueryWrapper<Workbench>().eq(USER_ID,workbenchDTO.getUserId()));
        //遍历获取页面id集合
        for (Workbench workbench:list) {
            savedSet.add(workbench.getMenuId());
        }
        //根据保存id和编辑后的id，获取需要新增和删除的页面
        Map<String,Set<Integer>> menuMap = Util.screenIdSet(savedSet,Util.arrayToSet(workbenchDTO.getMenuIds()));
        Set<Integer> menuInsert = menuMap.get(INSERT_SET) != null ? menuMap.get(INSERT_SET) : null;
        Set<Integer> menuDel = menuMap.get(DEL_SET) != null ? menuMap.get(DEL_SET) : null;
        //批量删除
        if(menuDel != null && menuDel.size()>0){
            for (Integer i:menuDel) {
                baseMapper.delete(new QueryWrapper<Workbench>().eq(MENU_ID,i).eq(USER_ID,workbenchDTO.getUserId()));
            }
        }
        //批量添加
        if(menuInsert != null && menuInsert.size()>0) {
            for (Integer i : menuInsert) {
                baseMapper.insert(new Workbench(workbenchDTO.getUserId(), i));
            }
        }
        return true;
    }

    @Override
    public Object findWorkbenchByUserID(Integer userID) throws Exception {
        //获取当前用户保存的工作台页面
        List<Workbench> list = baseMapper.selectList(new QueryWrapper<Workbench>().eq(USER_ID,userID));
        //获取菜单id集合
        Set<Integer> menuSet = new HashSet<Integer>();
        for (Workbench workbench:list) {
            menuSet.add(workbench.getMenuId());
        }
        if(menuSet.size()==0){
            return null;
        }
        List<AuthMenu> menuList = menuMapper.selectBatchIds(menuSet);
        return menuList;
    }

}
