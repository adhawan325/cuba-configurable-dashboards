package com.non.confdash.web.screens;

import com.haulmont.charts.gui.amcharts.model.*;
import com.haulmont.charts.gui.components.charts.PieChart;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.data.DataItem;
import com.haulmont.charts.gui.data.ListDataProvider;
import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.GridLayout;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.non.confdash.entity.ConfChartType;
import com.non.confdash.service.ChartDataService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigurableDashboardScreen extends AbstractWindow {
    @Inject
    private ChartDataService chartDataService;
    @Inject
    private GridLayout grid;
    @Inject
    private Metadata metadata;
    @Inject
    ComponentsFactory factory;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
    }

    @Override
    public void ready() {
        Map<String, Map<String, Long>> data = chartDataService.getData();
        for (String chartName : data.keySet()) {
            List<MapDataItem> mapDataItems = getMapDataItemFromMap(data.get(chartName));
            if (chartDataService.getChartTypeByName(chartName) == ConfChartType.pieChart) {
                grid.add(getPieChart(chartName, mapDataItems));
            }
            if (chartDataService.getChartTypeByName(chartName) == ConfChartType.serialChart) {
                grid.add(getSerialChart(chartName, mapDataItems));
            }
        }
        super.ready();
    }

    private SerialChart getSerialChart(String name, List<MapDataItem> mapDataItems) {
        SerialChart chart = factory.createComponent(SerialChart.class);
        chart.setDataProvider(getDataProviderFromMap(mapDataItems));
        chart.setCategoryField("key");
        Graph graph = new Graph();
        graph.setValueField("value");
        graph.setHidden(false);
         chart.addGraphs(graph);
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.setType(ValueAxisType.NUMERIC);
        valueAxis.setTitle("value");
        chart.addValueAxes(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        chart.setCategoryAxis(categoryAxis);
        chart.setHeight("100%");
        chart.setWidth("100%");
        chart.setAutoResize(true);
        chart.setVisible(true);
        chart.setCaption(name);
        chart.setLegend(new Legend());
        return chart;
    }

    private PieChart getPieChart(String name, List<MapDataItem> mapDataItems) {
        PieChart chart = factory.createComponent(PieChart.class);
        chart.setTitleField("key");
        chart.setValueField("value");
        for (MapDataItem item : mapDataItems) {
            chart.addData(item);
        }
        chart.setHeight("100%");
        chart.setWidth("100%");
        chart.setAutoResize(true);
        chart.setVisible(true);
        chart.setCaption(name);
        chart.setLabelsEnabled(false);
        chart.setLegend(new Legend());
        return chart;
    }

    private ListDataProvider getDataProviderFromMap(List<MapDataItem> mapDataItems) {
        ListDataProvider dataProvider = new ListDataProvider();

        for (MapDataItem item : mapDataItems) {
            dataProvider.addItem(item);
        }
        return dataProvider;
    }

    private List<MapDataItem> getMapDataItemFromMap(Map<String, Long> data) {
        List<MapDataItem> list = new ArrayList<>();
        for (String key : data.keySet()) {
            MapDataItem item = MapDataItem.of("key", key, "value", data.get(key));
            list.add(item);
        }
        return list;
    }
}
