package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 工作台注册快捷页面
 */

@Data
@TableName("workbench")
public class Workbench {

    @TableField("user_id")
    private Integer userId;
    @TableField("menu_id")
    private Integer menuId;

    public Workbench() {
    }


    public Workbench(Integer userId,Integer menuId) {
        this.userId = userId;
        this.menuId = menuId;
    }

}
