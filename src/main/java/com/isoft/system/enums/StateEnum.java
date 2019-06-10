package com.isoft.system.enums;

public enum StateEnum {

    TRI_YEAR(1),

    TRI_MONTH(2),

    TRI_DAY(3),

    TRI_HOUR(4),

    TRI_SECOND(5),

    TRI_WEEK(6);

    private Integer value;

    public Integer getValue() {
        return value;
    }

    StateEnum(Integer value) {
        this.value = value;
    }
}
