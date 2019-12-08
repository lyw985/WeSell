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
//		sb.append(" select concat(a.years,'-',a.months) as label1, a.weekdays as label2, ");
//		sb.append(" CASE ");
//		sb.append(" WHEN a.weekdays = 0 THEN '周一' ");
//		sb.append(" WHEN a.weekdays = 1 THEN '周二' ");
//		sb.append(" WHEN a.weekdays = 2 THEN '周三' ");
//		sb.append(" WHEN a.weekdays = 3 THEN '周四' ");
//		sb.append(" WHEN a.weekdays = 4 THEN '周五' ");
//		sb.append(" WHEN a.weekdays = 5 THEN '周六' ");
//		sb.append(" WHEN a.weekdays = 6 THEN '周日' ");
//		sb.append(" ELSE '其他' END AS label2,");
//		sb.append(" a.num as data  from( ");
//		sb.append(
//				" select year(t1.datelist) as years, month(t1.datelist) as months, weekday(t1.datelist) as weekdays, count(t2.id) as num");
//		sb.append(" from calendar t1");
//		sb.append(" left join yuma_order t2 on t1.datelist = t2.pay_date ");
//		sb.append(" where t1.datelist >= '2018-11-01' and t1.datelist < '2019-11-01' ");
//		sb.append(" GROUP BY year(t1.datelist), month(t1.datelist), weekday(t1.datelist) ");
//		sb.append(" ) a order by a.years,a.months,a.weekdays ");
		String sql=packageSql() ;
		System.out.println(sql);
//		String sql2="DELETE t1 FROM yuma_order t1 LEFT JOIN yuma_order_item t2 ON t1.id=t2.order_id WHERE t2.id IS NULL";
//		System.out.println(sql2);
//		this.getDao().getJdbcTemplate().execute(sql2);
		List<Map<String, Object>> mapList = this.getDao().getJdbcTemplate().queryForList(sql);
		return parseChartFromSqlResult(mapList);
	}

	private String packageSql() {
		String basic = "select ${labelData} from (select ${params} from ${tables} where 1=1 ${condition} ${groupBy} ) body ${orderBy} ${limit}";
		String labelData = "concat(body.years,'-',body.months) as label1, body.counts as data";
		String params = "year(cal.datelist) as years, month(cal.datelist) as months, count(yumaOrder.id) as counts, floor(sum(yumaOrder.pay_price)) as sums, floor(avg(yumaOrder.pay_price)) as avgs, max(yumaOrder.pay_price) as maxs";
		String tables = "calendar cal left join yuma_order yumaOrder on cal.datelist = yumaOrder.pay_date";
		String condition = "and cal.datelist >= '2018-11-01' and cal.datelist < '2019-11-01'";
		String groupBy = "group by year(cal.datelist), month(cal.datelist)";
		String orderBy = "order by body.years,body.months";
		String limit = "";
		basic = basic.replaceAll("\\$\\{labelData\\}", labelData);
		basic = basic.replaceAll("\\$\\{params\\}", params);
		basic = basic.replaceAll("\\$\\{tables\\}", tables);
		basic = basic.replaceAll("\\$\\{condition\\}", condition);
		basic = basic.replaceAll("\\$\\{groupBy\\}", groupBy);
		basic = basic.replaceAll("\\$\\{orderBy\\}", orderBy);
		basic = basic.replaceAll("\\$\\{limit\\}", limit);
		// TODO 补齐缺省的数据
		return basic;
	}

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		String basic = "select ${labelData} from (select ${params} from ${tables} where 1=1 ${condition} ${groups} ) body ${orders}";
		String labelData = "concat(body.years,'-',body.months) as label1, body.num as data";
		String params = "year(t1.datelist) as years, month(t1.datelist) as months, count(ord.id) as num";
		String tables = "calendar t1 left join yuma_order t2 on t1.datelist = t2.pay_date";
		String condition = "and t1.datelist >= '2018-11-01' and t1.datelist < '2019-11-01'";
		String groups = "group by year(t1.datelist), month(t1.datelist)";
		String orders = "order by body.years,body.months";
		basic = basic.replaceAll("\\$\\{labelData\\}", labelData);
		basic = basic.replaceAll("\\$\\{params\\}", params);
		basic = basic.replaceAll("\\$\\{tables\\}", tables);
		basic = basic.replaceAll("\\$\\{condition\\}", condition);
		basic = basic.replaceAll("\\$\\{groups\\}", groups);
		basic = basic.replaceAll("\\$\\{orders\\}", orders);
		System.out.println(basic);
	}

	private JQueryChart parseChartFromSqlResult(List<Map<String, Object>> mapList) {
		JQueryChart chart = new JQueryLineChart();
		LinkedHashSet<String> label1Set = new LinkedHashSet<String>();
		LinkedHashSet<String> label2Set = new LinkedHashSet<String>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		boolean singleLabel = false;
		for (Map<String, Object> map : mapList) {
			String label1 = (String) map.get("label1");
			String label2 = (String) map.get("label2");
			Object data = map.get("data");
			label1Set.add(label1);
			if (label2 != null) {
				label2Set.add(label2);
				dataMap.put(label1 + "#" + label2, data);
			} else {
				singleLabel = true;
				dataMap.put(label1, data);
			}
		}
		LinkedHashSet<String> xLabelSet = label1Set;
		if (singleLabel) {
			chart.setDataLabels(label1Set.toArray(new String[label1Set.size()]));
			List<Object> objectList = new ArrayList<Object>();
			for (String xLabel : xLabelSet) {
				Object data = dataMap.get(xLabel);
				objectList.add(data);
			}
			chart.addDataset("订单数量", objectList.toArray(new Object[objectList.size()]));
		} else {
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
		chart.setOptionsTitleText("asad");
		return chart;
	}

	class PackageSql {
		private StringBuilder select;
		private StringBuilder from;
		private StringBuilder condition;
		private StringBuilder group;
	}
}
