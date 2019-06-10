package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 数据权限
 */

@Data
public class DataAuth {
    private int id;
    //菜单id
    private Integer menuId;
    //角色id
    private Integer roleId;
    //权限内容
    private String content;
    //权限描述
    private String description;

    //权限描述
    @TableField(exist = false)
    private String menuName;

    //权限描述
    @TableField(exist = false)
    private String roleName;


}
