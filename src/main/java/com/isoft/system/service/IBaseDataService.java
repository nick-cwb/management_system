package com.isoft.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.entity.BaseDataOption;
import com.isoft.system.entity.dto.BaseDataDTO;
import jdk.nashorn.internal.ir.LiteralNode;

import java.util.List;

public interface IBaseDataService {

    //查询全部数据项目
    List<BaseDataOption> findOption(Integer dataId);
    //新增数据项目
    boolean saveOption(BaseDataOption dataOption);
    //修改数据项目
    boolean updOption(BaseDataOption dataOption);
    //删除数据项目
    boolean delOption(Integer id);
    //分页查询
    IPage<BaseDataOption> findAll(BaseDataDTO baseDataDTO);
    //根据code获取下拉列表项
    List<BaseDataOption> findOptionByCode(String codes);
}

