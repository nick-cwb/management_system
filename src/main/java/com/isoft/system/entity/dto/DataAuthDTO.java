package com.isoft.system.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.DataAuth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataAuthDTO extends Page<DataAuth> {

    @ApiModelProperty(value="数据权限描述")
    private String description;

    public String getDescription() {
        return "%"+this.description+"%";
    }

}
