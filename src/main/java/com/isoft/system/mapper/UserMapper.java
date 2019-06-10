package com.isoft.system.mapper;

import com.isoft.system.common.SupperMapper;
import com.isoft.system.entity.User;
import com.isoft.system.entity.dto.UserDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends SupperMapper<User> {
    /**
     * 根据机构分页查询用户
     * @param userDTO
     * @return
     */
    @Select("SELECT u.* " +
            "FROM org_emp o LEFT JOIN `user` u " +
            "ON o.emp_id = u.id " +
            "where o.org_id = #{orgId}")
    List<User> findAllByOrgId(UserDTO userDTO);

    /**
     * 根据机构id查询用户数
     * @param userDTO
     * @return
     */
    @Select("SELECT count(1)" +
            "FROM org_emp o LEFT JOIN `user` u " +
            "ON o.emp_id = u.id " +
            "where o.org_id = #{orgId}")
    long countPageSizeByOrgId(UserDTO userDTO);

    /**
     * 根据岗位id分页查询用户
     * @param userDTO
     * @return
     */
    @Select("SELECT u.* " +
            "FROM emp_position e LEFT JOIN `user` u " +
            "ON e.emp_id = u.id " +
            "where e.posi_id = #{poiId}")
    List<User> findAllByPosiId(UserDTO userDTO);

    /**
     * 根据岗位id查询用户数
     * @param userDTO
     * @return
     */
    @Select("SELECT u.* " +
            "FROM emp_position e LEFT JOIN `user` u " +
            "ON e.emp_id = u.id " +
            "where e.posi_id = #{poiId}")
    long countPageSizeByPosiId(UserDTO userDTO);

}
