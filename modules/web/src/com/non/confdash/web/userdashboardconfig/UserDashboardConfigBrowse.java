package com.non.confdash.web.userdashboardconfig;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.non.confdash.entity.UserDashboardConfig;

import javax.inject.Inject;
import java.util.UUID;

public class UserDashboardConfigBrowse extends AbstractLookup {
    @Inject
    private GroupDatasource<UserDashboardConfig, UUID> userDashboardConfigsDs;
    @Inject
    private Button createBtn;

    @Override
    public void ready() {
        if(userDashboardConfigsDs.getItems().size() > 0)
        {
            createBtn.setEnabled(false);
        }
        super.ready();
    }
}