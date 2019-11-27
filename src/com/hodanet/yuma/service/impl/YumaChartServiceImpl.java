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

/**
 * @anthor lyw
 * @yumaItemModel 2016-11-11 10:34:32
 */
@Service
public class YumaChartServiceImpl extends AbstractDaoService implements YumaChartService {

	@Override
	public JQueryChart getChart() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select concat(a.years,'-',a.months) as label1, a.weekdays as label2, ");
		sb.append(" CASE ");
		sb.append(" WHEN a.weekdays = 0 THEN '周一' ");
		sb.append(" WHEN a.weekdays = 1 THEN '周二' ");
		sb.append(" WHEN a.weekdays = 2 THEN '周三' ");
		sb.append(" WHEN a.weekdays = 3 THEN '周四' ");
		sb.append(" WHEN a.weekdays = 4 THEN '周五' ");
		sb.append(" WHEN a.weekdays = 5 THEN '周六' ");
		sb.append(" WHEN a.weekdays = 6 THEN '周日' ");
		sb.append(" ELSE '其他' END AS label2,");
		sb.append(" a.num as data  from( ");
		sb.append(
				" select year(t1.datelist) as years, month(t1.datelist) as months, weekday(t1.datelist) as weekdays, count(t2.id) as num");
		sb.append(" from calendar t1");
		sb.append(" left join yuma_order t2 on t1.datelist = t2.pay_date ");
		sb.append(" where t1.datelist >= '2018-11-01' and t1.datelist < '2019-11-01' ");
		sb.append(" GROUP BY year(t1.datelist), month(t1.datelist), weekday(t1.datelist) ");
		sb.append(" ) a order by a.years,a.months,a.weekdays ");
		List<Map<String, Object>> mapList = this.getDao().getJdbcTemplate().queryForList(sb.toString());
		JQueryChart chart = new JQueryLineChart();
		LinkedHashSet<String> label1Set = new LinkedHashSet<String>();
		LinkedHashSet<String> label2Set = new LinkedHashSet<String>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for (Map<String, Object> map : mapList) {
			String label1 = (String) map.get("label1");
			String label2 = (String) map.get("label2");
			Object data = map.get("data");
			label1Set.add(label1);
			label2Set.add(label2);
			dataMap.put(label1 + "#" + label2, data);
		}
		boolean changeXY = false;
		LinkedHashSet<String> xLabelSet = label1Set;
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
		return chart;
	}

}
