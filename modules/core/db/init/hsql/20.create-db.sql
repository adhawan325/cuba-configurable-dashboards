-- begin NONCCDB_USER_DASHBOARD_CONFIG_DASHBOARD_CONFIG_LINK
alter table NONCCDB_USER_DASHBOARD_CONFIG_DASHBOARD_CONFIG_LINK add constraint FK_USEDASCONDASCON_ON_USER_DASHBOARD_CONFIG foreign key (USER_DASHBOARD_CONFIG_ID) references NONCCDB_USER_DASHBOARD_CONFIG(ID)^
alter table NONCCDB_USER_DASHBOARD_CONFIG_DASHBOARD_CONFIG_LINK add constraint FK_USEDASCONDASCON_ON_DASHBOARD_CONFIG foreign key (DASHBOARD_CONFIG_ID) references NONCCDB_DASHBOARD_CONFIG(ID)^
-- end NONCCDB_USER_DASHBOARD_CONFIG_DASHBOARD_CONFIG_LINK
