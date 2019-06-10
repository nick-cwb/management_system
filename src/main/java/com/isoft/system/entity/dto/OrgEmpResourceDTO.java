package com.isoft.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 处理员工权限资源
 */

@Data
public class OrgEmpResourceDTO {

    @ApiModelProperty(value="参与id")
    private Integer id;

    @ApiModelProperty(value="组织id")
    private Integer[] organizationIds;

    
}
