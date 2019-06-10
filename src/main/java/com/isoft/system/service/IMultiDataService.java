package com.isoft.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isoft.system.entity.MultiData;

import java.util.List;

public interface IMultiDataService extends IService<MultiData> {

    List<MultiData> findAll();

}
