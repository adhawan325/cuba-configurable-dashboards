package com.non.confdash.web.screens;

import com.haulmont.charts.gui.amcharts.model.*;
import com.haulmont.charts.gui.components.charts.PieChart;
import com.haulmont.charts.gui.components.charts.SerialChart;
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
            ConfChartType chartType = chartDataService.getChartTypeByName(chartName);
            if (chartType == ConfChartType.PIE) {
                grid.add(getPieChart(chartName, mapDataItems));
            }
            else {
                grid.add(getSerialChart(chartName, mapDataItems, chartType ));
            }
        }
        super.ready();
    }

    private SerialChart getSerialChart(String name, List<MapDataItem> mapDataItems, ConfChartType type) {
        SerialChart chart = factory.createComponent(SerialChart.class);

        chart.setDataProvider(getDataProviderFromMap(mapDataItems));
        chart.setCategoryField("key");

        chart.addGraphs(getGraph(type));
        ValueAxis valueAxis = new ValueAxis();
        valueAxis.setType(ValueAxisType.NUMERIC);
        valueAxis.setTitle("value");
        chart.addValueAxes(valueAxis);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setLabelRotation(45);
        chart.setCategoryAxis(categoryAxis);

        chart.setHeight("100%");
        chart.setWidth("100%");
        chart.setAutoResize(true);
        chart.setVisible(true);
        chart.setCaption(name);
        chart.setLegend(new Legend());
        return chart;
    }

    private Graph getGraph(ConfChartType type)
    {
        Graph graph = new Graph();
        graph.setValueField("value");
        if( type == ConfChartType.COLUMN) {graph.setType(GraphType.COLUMN);}
        else if( type == ConfChartType.SMOOTHED_LINE) {graph.setType(GraphType.SMOOTHED_LINE);}
        else if( type == ConfChartType.STEP) {graph.setType(GraphType.STEP);}
        else {graph.setType(GraphType.LINE);}

        graph.setFillAlphas(Double.valueOf(0.5));
        graph.setHidden(false);
        return  graph;
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
