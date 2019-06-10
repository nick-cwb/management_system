package com.isoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.Workbench;
import com.isoft.system.entity.dto.WorkbenchDTO;

public interface IWorkbenchService extends IService<Workbench> {

    //更新工作台
    Object updateWorkbench(WorkbenchDTO workbenchDTO) throws Exception;

    //查询当前用户的工作台
    Object findWorkbenchByUserID(Integer userID) throws Exception;

}
