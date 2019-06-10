package com.isoft.system.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isoft.system.entity.SysLogs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysLogsDTO extends Page<SysLogs> {

    @ApiModelProperty(value="日志id")
    private Integer id;

    @ApiModelProperty(value="模块名称")
    private String module;


    public String getModule() {

        return "%"+this.module+"%";
    }


}
