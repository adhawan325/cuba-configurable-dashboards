package com.non.confdash.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@NamePattern("%s|name")
@Table(name = "NONCCDB_DASHBOARD_CONFIG")
@Entity(name = "nonccdb$DashboardConfig")
public class DashboardConfig extends StandardEntity {
    private static final long serialVersionUID = 1578071646702684612L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Column(name = "ENTITY", nullable = false)
    protected String entity;

    @NotNull
    @Column(name = "FIELD", nullable = false)
    protected String field;

    @Lob
    @Column(name = "QUERY")
    protected String query;

    @NotNull
    @Column(name = "TYPE_", nullable = false)
    protected Integer type;

    @JoinTable(name = "NONCCDB_USER_DASHBOARD_CONFIG_DASHBOARD_CONFIG_LINK",
        joinColumns = @JoinColumn(name = "DASHBOARD_CONFIG_ID"),
        inverseJoinColumns = @JoinColumn(name = "USER_DASHBOARD_CONFIG_ID"))
    @ManyToMany
    protected List<UserDashboardConfig> userDashboardConfigs;

    public void setType(ConfChartType type) {
        this.type = type == null ? null : type.getId();
    }

    public ConfChartType getType() {
        return type == null ? null : ConfChartType.fromId(type);
    }


    public void setUserDashboardConfigs(List<UserDashboardConfig> userDashboardConfigs) {
        this.userDashboardConfigs = userDashboardConfigs;
    }

    public List<UserDashboardConfig> getUserDashboardConfigs() {
        return userDashboardConfigs;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }


}