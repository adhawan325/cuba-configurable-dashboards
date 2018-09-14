package com.non.confdash.web.dashboardconfig;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.ResizableTextArea;
import com.haulmont.cuba.gui.components.TextField;
import com.non.confdash.entity.DashboardConfig;
import com.non.confdash.service.DashboardService;
import com.non.dta.service.DuplicateRuleService;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.TreeMap;

public class DashboardConfigEdit extends AbstractEditor<DashboardConfig> {
    @Named("fieldGroup.entity")
    private TextField entityField;
    @Inject
    private LookupField entityLookup;
    @Inject
    private DuplicateRuleService duplicateRuleService;
    @Inject
    private DashboardService dashboardService;
    @Inject
    private LookupField fieldLookup;
    @Named("fieldGroup.field")
    private TextField fieldField;
    @Named("fieldGroup.query")
    private ResizableTextArea queryField;
    @Inject
    private Metadata metadata;
    @Inject
    private LookupField groupBy;
    @Named("fieldGroup.groupBy")
    private TextField groupByField;

    @Override
    public void init(Map<String, Object> params) {
        groupBy.setVisible(false);
        entityLookup.setOptionsMap(getEntitiesLookupFieldOptions());
        entityLookup.setRequired(true);

        entityLookup.addValueChangeListener(e -> {
            DashboardConfig dashboardConfig = getItem();
            MetaClass baseClass = entityLookup.getValue();
            dashboardConfig.setEntity(baseClass.getName());
            fieldLookup.setOptionsMap(getAttributes(baseClass));
            if( entityLookup.getValue() != null )
            {
                fieldLookup.setEditable(true);
            }
        });
        fieldLookup.addValueChangeListener(e -> {
            DashboardConfig dashboardConfig = getItem();
            MetaProperty property = fieldLookup.getValue();
            dashboardConfig.setField(property.getName());
            if( property.getJavaType().getSuperclass() != null && property.getJavaType().getSuperclass().equals(StandardEntity.class))
            {
                groupBy.setOptionsMap(getAttributes(metadata.getClass(property.getJavaType())));
                groupBy.setVisible(true);
                groupBy.setRequired(true);
            }
            else
            {
                groupBy.setVisible(false);
                groupBy.setRequired(false);
                getItem().setGroupBy(null);
            }
            dashboardConfig.setQuery(dashboardService.getQueryStringForCount(entityField.getValue(), fieldField.getValue()));

        });

        groupBy.addValueChangeListener(e -> {
            DashboardConfig dashboardConfig = getItem();
            MetaProperty property = groupBy.getValue();
            dashboardConfig.setGroupBy(property.getName());
            dashboardConfig.setQuery(dashboardService.getQueryStringForCount(entityField.getValue(), fieldField.getValue(), ((MetaProperty)groupBy.getValue()).getName()));
        });

        super.init(params);
    }

    @Override
    protected void postInit() {
        if(!StringUtils.isEmpty(entityField.getValue()) )
        {
            entityLookup.setValue(metadata.getClass(entityField.getValue().toString()));
        }
        if( !StringUtils.isEmpty(fieldField.getValue()) )
        {
            fieldLookup.setValue(metadata.getClass(entityField.getValue().toString()).getProperty(fieldField.getValue().toString()));
        }
        if( !StringUtils.isEmpty(groupByField.getValue()))
        {
            groupBy.setValue(metadata.getClass(metadata.getClass(entityField.getValue().toString()).getProperty(fieldField.getValue().toString()).getJavaType()).getProperty(groupByField.getValue().toString()));
        }
    }

    protected Map<String, Object> getEntitiesLookupFieldOptions() {
        Map<String, Object> options = new TreeMap<>();
        for (MetaClass metaClass : duplicateRuleService.getAccessibleEntities()) {
            options.put(messages.getTools().getEntityCaption(metaClass) + " (" + metaClass.getName() + ")", metaClass);
        }
        return options;
    }

    protected Map<String, Object> getAttributes(MetaClass metaClass) {
        Map<String, Object> options = new TreeMap<>();

        for (MetaProperty metaProperty : metaClass.getProperties()) {
            options.put(metaProperty.getName(), metaProperty);
        }
        return options;
    }
}