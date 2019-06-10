package com.isoft.system.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JsonResult {

    @ApiModelProperty(value="结果状态：成功success、失败failure、完成complete")
    public String resultStatus;

    @ApiModelProperty(value="结果信息")
    public String message;

    @ApiModelProperty(value="结果对象")
    public Object data;

}
