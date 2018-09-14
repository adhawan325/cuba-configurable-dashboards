package com.non.confdash.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ConfChartType implements EnumClass<Integer> {
    PIE(10),
    LINE(20),
    COLUMN(30),
    STEP(40),
    SMOOTHED_LINE(50);

    private Integer id;

    ConfChartType(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ConfChartType fromId(Integer id) {
        for (ConfChartType at : ConfChartType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}