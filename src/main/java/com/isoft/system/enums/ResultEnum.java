package com.isoft.system.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum ResultEnum implements IEnum {

	PASS_MODIFY_SUCCESS("密码修改成功"),
    ADD_SUCCESS("新增成功"),
    UPDATE_SUCCESS("修改成功"),
    DELETE_SUCCESS("删除成功"),
    DELETE_FAILURE("删除失败"),
    NONE_RESULT("查无数据");

    private final String value;

    ResultEnum(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    @Override
    public Serializable getValue() {
        return this.value;
    }
}
