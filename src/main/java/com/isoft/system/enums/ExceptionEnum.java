package com.isoft.system.enums;

public enum ExceptionEnum {

    UNAUTHORIZED_EXCEPTION("访问未授权"),
    AUTHORIZATION_EXCEPTION("权限验证失败"),
    PARAMETER_MATCHING_EXCEPTION("参数格式错误"),
    NUMBER_FORMAT_EXCEPTION("数据格式错误");

    private final String value;

    ExceptionEnum(String value) {
        this.value = value;
    }

    public String value(){
        return this.value;
    }
}
