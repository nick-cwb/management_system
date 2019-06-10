package com.isoft.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isoft.system.entity.MultiData;
import com.isoft.system.multiDatasource.MultiDataMapper;
import com.isoft.system.service.IMultiDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("slave")
public class MultiDataServiceImpl extends ServiceImpl<MultiDataMapper,MultiData> implements IMultiDataService {
    @Override
    public List<MultiData> findAll() {
        List<MultiData> list = baseMapper.findAll();
        return list;
    }
}
