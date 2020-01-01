package com.hodanet.yuma.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.yuma.entity.vo.jquery.JQueryChart;
import com.hodanet.yuma.entity.vo.jquery.JQueryLineChart;
import com.hodanet.yuma.service.YumaChartService;
import com.hodanet.yuma.service.impl.chart.ChartQueryFactory;

/**
 * @anthor lyw
 * @yumaItemModel 2016-11-11 10:34:32
 */
@Service
public class YumaChartServiceImpl extends AbstractDaoService implements YumaChartService {

	@Override
	public JQueryChart getChart(ChartQueryFactory chartQueryFactory) {
		String sql = chartQueryFactory.toSqlString();
		System.out.println(sql);
		List<Object> params = chartQueryFactory.getConditionValues();
		List<Map<String, Object>> mapList = this.getDao().getJdbcTemplate().queryForList(sql,
				params.toArray(new Object[params.size()]));
		JQueryChart chart = parseChartFromSqlResult(mapList, chartQueryFactory);
		return chart;
	}

	private JQueryChart parseChartFromSqlResult(List<Map<String, Object>> mapList,
			ChartQueryFactory chartQueryFactory) {
		JQueryChart chart = new JQueryLineChart();
		LinkedHashSet<String> label1Set = new LinkedHashSet<String>();
		LinkedHashSet<String> label2Set = new LinkedHashSet<String>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int labelNum = 0;
		for (Map<String, Object> map : mapList) {
			Object data = map.get("data");
			String label1 = (String) map.get("label1");
			String label2 = (String) map.get("label2");
			String key = "single";
			if (label1 != null) {
				label1Set.add(label1);
				labelNum = 1;
				key = label1;
			}
			if (label2 != null) {
				label2Set.add(label2);
				labelNum = 2;
				key += "#" + label2;
			}
			dataMap.put(key, data);
		}
		LinkedHashSet<String> xLabelSet = label1Set;
		if (labelNum == 0) {
			chart.setDataLabels(new String[] { "数据" });
			chart.addDataset("总量", new Object[] { dataMap.get("single") });
		} else if (labelNum == 1) {
			chart.setDataLabels(label1Set.toArray(new String[label1Set.size()]));
			List<Object> objectList = new ArrayList<Object>();
			for (String xLabel : xLabelSet) {
				Object data = dataMap.get(xLabel);
				objectList.add(data);
			}
			chart.addDataset(chartQueryFactory.getLabelTip(), objectList.toArray(new Object[objectList.size()]));
		} else if (labelNum == 2) {
			boolean changeXY = false;
			LinkedHashSet<String> yLabelSet = label2Set;
			if (label1Set.size() < label2Set.size()) {
				xLabelSet = label2Set;
				yLabelSet = label1Set;
				changeXY = true;
			}
			chart.setDataLabels(xLabelSet.toArray(new String[xLabelSet.size()]));
			for (String yLabel : yLabelSet) {
				List<Object> objectList = new ArrayList<Object>();
				for (String xLabel : xLabelSet) {
					String key = xLabel + "#" + yLabel;
					if (changeXY) {
						key = yLabel + "#" + xLabel;
					}
					Object data = dataMap.get(key);
					objectList.add(data);
				}
				chart.addDataset(yLabel, objectList.toArray(new Object[objectList.size()]));
			}
		}
		chart.setOptionsTitleText(chartQueryFactory.getOptionsTitleText());
		return chart;
	}

	class PackageSql {
		private StringBuilder select;
		private StringBuilder from;
		private StringBuilder condition;
		private StringBuilder group;
	}
}
