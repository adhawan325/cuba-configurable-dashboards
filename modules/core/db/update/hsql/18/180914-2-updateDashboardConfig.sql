alter table NONCCDB_DASHBOARD_CONFIG add column TYPE_ integer ^
update NONCCDB_DASHBOARD_CONFIG set TYPE_ = 10 where TYPE_ is null ;
alter table NONCCDB_DASHBOARD_CONFIG alter column TYPE_ set not null ;
