-- begin NONCCDB_DASHBOARD_CONFIG
create table NONCCDB_DASHBOARD_CONFIG (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    ENTITY varchar(255) not null,
    FIELD varchar(255) not null,
    QUERY longvarchar,
    TYPE_ integer not null,
    --
    primary key (ID)
)^
-- end NONCCDB_DASHBOARD_CONFIG
-- begin NONCCDB_USER_DASHBOARD_CONFIG
create table NONCCDB_USER_DASHBOARD_CONFIG (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    USER_ID varchar(36) not null,
    --
    primary key (ID)
)^
-- end NONCCDB_USER_DASHBOARD_CONFIG
-- begin NONCCDB_USER_DASHBOARD_CONFIG_DASHBOARD_CONFIG_LINK
create table NONCCDB_USER_DASHBOARD_CONFIG_DASHBOARD_CONFIG_LINK (
    USER_DASHBOARD_CONFIG_ID varchar(36) not null,
    DASHBOARD_CONFIG_ID varchar(36) not null,
    primary key (USER_DASHBOARD_CONFIG_ID, DASHBOARD_CONFIG_ID)
)^
-- end NONCCDB_USER_DASHBOARD_CONFIG_DASHBOARD_CONFIG_LINK
