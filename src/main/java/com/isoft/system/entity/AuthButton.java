package com.isoft.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限按钮表
 */
@Data
@TableName("auth_button")
public class AuthButton {

    @ApiModelProperty(value="用户id")
    @TableField("id")
    @TableId(type = IdType.AUTO)
    private int id;

    @ApiModelProperty(value="按钮名称")
    @TableField("btn_name")
    private String btnName;

    @ApiModelProperty(value="按钮标签")
    @TableField("btn_label")
    private String btnLabel;

    @ApiModelProperty(value="按钮编码")
    @TableField("btn_code")
    private String btnCode;

    @ApiModelProperty(value="按钮关联菜单编码")
    @TableField("menu_code")
    private String menuCode;

    @ApiModelProperty(value="逻辑删除")
    @TableLogic
    @TableField("is_del")
    private Integer isDel;

}
