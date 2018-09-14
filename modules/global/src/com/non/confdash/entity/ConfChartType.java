package com.non.confdash.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ConfChartType implements EnumClass<Integer> {

    pieChart(10),
    serialChart(20);

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