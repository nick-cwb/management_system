package com.isoft.system.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

public enum NumberEnum implements IEnum {

    ZORO(0,"0"),
    ONE(1,"1"),
    TWO(2,"2"),
    FIVE(5,"5");
    private final int key;
    private final String value;

    NumberEnum(final int key, final String value) {
        this.key = key;
        this.value = value;
    }

    public int key() {
        return this.key;
    }

    public String value() {
        return this.value;
    }


    @Override
    public Serializable getValue() {
        return null;
    }
}
