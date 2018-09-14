package com.non.confdash.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.non.confdash.entity.ConfChartType;
import com.non.confdash.entity.DashboardConfig;
import com.non.confdash.entity.UserDashboardConfig;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service(ChartDataService.NAME)
public class ChartDataServiceBean implements ChartDataService {
    @Inject
    private DataManager dataManager;
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private Metadata metadata;
    @Inject
    private Persistence persistence;

    @Override
    public Map<String, Map<String, Long>> getData() {
        Map<String, Map<String, Long>> chartMap = new HashMap<>();
        LoadContext<UserDashboardConfig> loadContext = new LoadContext<>(UserDashboardConfig.class);
        loadContext.setQuery(LoadContext.createQuery("select e from nonccdb$UserDashboardConfig e where e.userId = :userId").setParameter("userId", userSessionSource.currentOrSubstitutedUserId()));
        loadContext.setView("userDashboardConfig-view");
        List<UserDashboardConfig> list = dataManager.loadList(loadContext);
        for (UserDashboardConfig userDashboardConfig : list) {
            List<DashboardConfig> dashboardConfigs = userDashboardConfig.getDashboard();
            for (DashboardConfig dashboardConfig : dashboardConfigs) {
                chartMap.put(dashboardConfig.getName(), executeQuery(metadata.getClass(dashboardConfig.getEntity()).getJavaClass(), dashboardConfig.getQuery()));
            }
        }

        return chartMap;
    }

    @Override
    public ConfChartType getChartTypeByName(String name) {
        LoadContext<DashboardConfig> loadContext = LoadContext.create(DashboardConfig.class);
        loadContext.setQuery(LoadContext.createQuery("select e from nonccdb$DashboardConfig e where e.name = :name").setParameter("name", name));
        return (dataManager.load(loadContext).getType());
    }

    private Map<String, Long> executeQuery(Class metaClass, String query) {
        Map<String, Long> map = new HashMap();

        Transaction tx = persistence.createTransaction();
        EntityManager entityManager = persistence.getEntityManager();
        List list = entityManager.createQuery(query).getResultList();

        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Object[] row = (Object[]) it.next();
            Long count = (Long) row[0];
            String name = (String) row[1];
            map.put(name, count);
        }

        tx.end();
        return map;
    }
}