package com.isoft.system.entity.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.PartyAuth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PartyAuthDTO extends Page<PartyAuth>{

	@ApiModelProperty(value="id")
    private Integer id;

    @ApiModelProperty(value="参与类型")
    private String partyType;

}
