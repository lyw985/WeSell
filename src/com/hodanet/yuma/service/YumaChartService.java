package com.hodanet.yuma.service;

import com.hodanet.yuma.entity.vo.jquery.JQueryChart;
import com.hodanet.yuma.service.impl.chart.ChartQueryFactory;

/**
 * @anthor lyw
 * @yumaItemModel 2016-11-11 10:34:32
 */
public interface YumaChartService {
	public JQueryChart getChart(ChartQueryFactory chartQueryFactory);
	
}
