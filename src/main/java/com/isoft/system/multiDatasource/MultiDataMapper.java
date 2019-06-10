package com.isoft.system.multiDatasource;

import com.isoft.system.common.SupperMapper;
import com.isoft.system.entity.MultiData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MultiDataMapper extends SupperMapper<MultiData> {

    @Select("select * from multi_data")
    List<MultiData> findAll();

}
