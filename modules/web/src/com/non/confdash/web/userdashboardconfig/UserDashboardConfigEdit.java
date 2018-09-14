package com.non.confdash.web.userdashboardconfig;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.MaskedField;
import com.haulmont.cuba.security.global.UserSession;
import com.non.confdash.entity.UserDashboardConfig;
import com.non.confdash.service.ChartDataService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

public class UserDashboardConfigEdit extends AbstractEditor<UserDashboardConfig> {
    @Named("fieldGroup.userId")
    private MaskedField userIdField;
    @Inject
    private UserSession userSession;
    @Inject
    private ChartDataService chartDataService;

    @Override
    public void init(Map<String, Object> params) {
        userIdField.setEditable(false);
    }

    @Override
    protected void postInit() {
        getItem().setUserId(userSession.getCurrentOrSubstitutedUser().getId());
        super.postInit();
    }
}