package com.non.confdash.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|dashboard")
@Table(name = "NONCCDB_USER_DASHBOARD_CONFIG")
@Entity(name = "nonccdb$UserDashboardConfig")
public class UserDashboardConfig extends StandardEntity {
    private static final long serialVersionUID = 1098461254365461782L;

    @NotNull
    @Column(name = "USER_ID", nullable = false)
    protected UUID userId;

    @JoinTable(name = "NONCCDB_USER_DASHBOARD_CONFIG_DASHBOARD_CONFIG_LINK",
        joinColumns = @JoinColumn(name = "USER_DASHBOARD_CONFIG_ID"),
        inverseJoinColumns = @JoinColumn(name = "DASHBOARD_CONFIG_ID"))
    @ManyToMany
    protected List<DashboardConfig> dashboard;

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setDashboard(List<DashboardConfig> dashboard) {
        this.dashboard = dashboard;
    }

    public List<DashboardConfig> getDashboard() {
        return dashboard;
    }


}