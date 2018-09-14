package com.non.confdash.service;


import com.non.confdash.entity.ConfChartType;

import java.util.Map;

public interface ChartDataService {
    String NAME = "nonccdb_ChartDataService";

    Map<String, Map<String, Long>> getData();

    ConfChartType getChartTypeByName(String name);
}