package com.isoft.system.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.AuthButton;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthButtonDTO extends Page<AuthButton> {

    @ApiModelProperty(value="按钮名称")
    private String btnName;

    @ApiModelProperty(value="按钮标签")
    private String btnLabel;

    @ApiModelProperty(value="按钮编码")
    private String btnCode;

    @ApiModelProperty(value="按钮关联菜单编码")
    private String menuCode;
}
