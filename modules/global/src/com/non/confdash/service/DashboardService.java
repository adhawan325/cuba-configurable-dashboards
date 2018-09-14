package com.non.confdash.service;


public interface DashboardService {
    String NAME = "nonccdb_DashboardService";

    String getQueryStringForCount(String entity, String field);

    String getQueryStringForCount(String entity, String field, String groupBy);
}