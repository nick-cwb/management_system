package com.isoft.system.enums;

public enum  BusinessExceptionEnum {

    WRONG_PASSWORD("用户名密码错误"),
    ACCOUNT_EXIST("账户已存在"),
    ROLE_EXIST("角色编码已存在"),
    BUTTON_EXIST("按钮编码已存在"),
    MENU_EXIST("菜单编码已存在"),
    WRONG_MENU_CODE("菜单编码错误"),
    ORGANIZATION_EXIST("机构编码已存在"),
    POSITION_EXIST("岗位编码已存在"),
    NULL_RESOURCE_AUTHORIZED("该角色未进行资源授权"),
    WRONG_OLD_PASSWORD("原密码错误"),
    NULL_MENU_CODE("菜单编码为空"),
    NULL_DATA_CODE("基础数据编码为空"),
    DATA_CODE_EXIST("基础数据编码已存在"),
    WRONG_DATA_CODE("基础数据编码错误"),
    VACATION_DAYS_NOT_ENOUGH("调休余额不足"),
    ACCOUNT_FREEZE("账号已冻结");
    private final String value;

    BusinessExceptionEnum(String value) {
        this.value = value;
    }

    public String value(){
        return this.value;
    }

}
