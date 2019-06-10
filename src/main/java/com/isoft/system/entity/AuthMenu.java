package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 菜单权限
 */

@Data
@TableName("auth_menu")
public class AuthMenu {

    @ApiModelProperty(value="菜单id")
    @TableField("id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value="菜单名称")
    @TableField("menu_name")
    private String name;

    @ApiModelProperty(value="菜单标签")
    @TableField("menu_label")
    private String menuLabel;

    @ApiModelProperty(value="菜单编码")
    @TableField("menu_code")
    private String menuCode;

    @ApiModelProperty(value="菜单参数")
    @TableField("parameter")
    private String parameter;

    @ApiModelProperty(value="排列顺序")
    @TableField("menu_order")
    private Integer menuOrder;

    @ApiModelProperty(value="菜单的图标地址")
    @TableField("menu_icon")
    private String menuIcon;

    @ApiModelProperty(value="新开tab展示、当前页展示")
    @TableField("menu_open_mode")
    private Integer menuOpenMode;


    @ApiModelProperty(value="父级编码")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value="地址")
    @TableField("path")
    private String path;

    @ApiModelProperty(value="页面相对路径")
    @TableField("component")
    private String component;

    @ApiModelProperty(value="是否开启页面缓存")
    @TableField("not_cache")
    private Integer notCache;

    @ApiModelProperty(value="逻辑删除")
    @TableLogic
    @TableField("is_del")
    private Integer isDel;

    @ApiModelProperty(value="页面缓存，将notCache的int型转为boolean")
    @TableField(exist = false)
    private boolean cacheStatus;

    @ApiModelProperty(value="孩子节点")
    @TableField(exist = false)
    private List<AuthMenu> children;
}
