package com.non.confdash.service;

import org.springframework.stereotype.Service;

@Service(DashboardService.NAME)
public class DashboardServiceBean implements DashboardService {



    @Override
    public String getQueryStringForCount(String entity, String field)
    {
        String queryString = "";
        queryString += "select count(e) as count_, e." + field + " as " + field + "_ from " + entity + " e group by e." + field;
        return queryString;
    }

    @Override
    public String getQueryStringForCount(String entity, String field, String groupBy)
    {
        String queryString = "";
        queryString += "select count(e) as count_, e." + field + "." + groupBy + " as " + field + "_ from " + entity + " e group by e." + field + "." + groupBy;
        return queryString;
    }
}