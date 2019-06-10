package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位信息
 */

@Data
public class PositionNameDTO {


    @ApiModelProperty(value="岗位id")
    private Integer id;

    @ApiModelProperty(value="岗位名称")
    private String posiName;

}
