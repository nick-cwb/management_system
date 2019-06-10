package com.isoft.system.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.Organization;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrganizationDTO extends Page<Organization> {

    @ApiModelProperty(value="机构编号")
    private String orgCode;

    @ApiModelProperty(value="机构名称")
    private String orgName;

}
