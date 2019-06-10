package com.isoft.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.isoft.system.common.SupperMapper;
import com.isoft.system.entity.DataAuth;
import com.isoft.system.entity.dto.DataAuthDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DataAuthMapper extends SupperMapper<DataAuth> {

    @Select("SELECT d.*,m.menu_label menuName,r.role_name roleName FROM data_auth d " +
            "LEFT JOIN auth_menu m " +
            "ON d.menu_id = m.id " +
            "LEFT JOIN role r " +
            "ON r.id = d.role_id " +
            "WHERE d.description LIKE #{description} ")
    List<DataAuth> findAllData(DataAuthDTO dataAuthDTO);

    @Select("SELECT count(0) FROM data_auth d " +
            "LEFT JOIN auth_menu m " +
            "ON d.menu_id = m.id " +
            "LEFT JOIN role r " +
            "ON r.id = d.role_id " +
            "WHERE d.description LIKE #{description} ")
    long countAll(DataAuthDTO dataAuthDTO);

}
