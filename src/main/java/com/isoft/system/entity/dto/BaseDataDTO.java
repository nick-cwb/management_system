package com.isoft.system.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.BaseDataOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseDataDTO extends Page<BaseDataOption> {
    @ApiModelProperty(value="基础数据名称")
    private String name;

    @ApiModelProperty(value="基础数据编码")
    private String optionCode;
}
