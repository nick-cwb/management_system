package com.isoft.system.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.Position;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PositionDTO extends Page<Position> {

    @ApiModelProperty(value="岗位编码")
    private String posiCode;

    @ApiModelProperty(value="岗位名称")
    private String posiName;

}
