package com.hodanet.yuma.service.impl.chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hodanet.yuma.constant.YumaOrderItemStatus;

public class ChartQueryFactory {
	private ChartParameterEnum chartParameter;
	// 这种存储方式仅适用于同类条件只能出现一次
	private Map<ChartConditionEnum, Object> chartConditions = new HashMap<ChartConditionEnum, Object>();
	private Set<ChartTableEnum> chartTables = new HashSet<ChartTableEnum>();
	private List<ChartGroupByEnum> chartGroupBys = new ArrayList<ChartGroupByEnum>();
	private List<Object> conditionValues = new ArrayList<Object>();

	public List<Object> getConditionValues() {
		return conditionValues;
	}

	public ChartQueryFactory() {
		setCondition("orderItemStatusNotEqual", YumaOrderItemStatus.FACKE.getValue());
	}

	public String getOptionsTitleText() {
		return chartParameter.getTip();
	}

	public String getLabelTip() {
		return chartParameter.getTip();
	}

	public String toSqlString() {
		String basic = "select ${groupByParams} ${dataParams} from ${tables} where 1=1 ${conditions} ${groupBy} ${orderBy} ${limit}";

		String groupByParams = loadgroupByParamsSql();
		basic = basic.replaceAll("\\$\\{groupByParams\\}", groupByParams);

		String dataParams = loadDataParamsSql();
		basic = basic.replaceAll("\\$\\{dataParams\\}", dataParams);

		String tables = loadTablesSql();
		basic = basic.replaceAll("\\$\\{tables\\}", tables);

		String conditions = loadConditionsSql();
		basic = basic.replaceAll("\\$\\{conditions\\}", conditions);

		String groupBy = loadGroupBySql();
		basic = basic.replaceAll("\\$\\{groupBy\\}", groupBy);

		basic = basic.replaceAll("\\$\\{orderBy\\}", "");
		basic = basic.replaceAll("\\$\\{limit\\}", "");
		return basic;
	}

	private String loadGroupBySql() {
		StringBuilder sb = new StringBuilder();
		if (chartGroupBys != null && chartGroupBys.size() > 0) {
			sb.append(" group by ");
			for (int i = 0; i < chartGroupBys.size(); i++) {
				ChartGroupByEnum chartGroupByEnum = chartGroupBys.get(i);
				sb.append(chartGroupByEnum);
				if (i != chartGroupBys.size() - 1) {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}

	private String loadConditionsSql() {
		StringBuilder sb = new StringBuilder();
		if (chartConditions != null) {
			for (ChartConditionEnum chartConditionEnum : chartConditions.keySet()) {
				sb.append(" and ").append(chartConditionEnum);
				conditionValues.add(chartConditions.get(chartConditionEnum));
			}
		}
		return sb.toString();
	}

	private String loadTablesSql() {
		StringBuilder sb = new StringBuilder();
		if (chartTables != null) {
			List<ChartTableEnum> chartTableList = new ArrayList<ChartTableEnum>();
			for (ChartTableEnum chartTableEnum : chartTables) {
				chartTableList.add(chartTableEnum);
			}
			Collections.sort(chartTableList, new Comparator<ChartTableEnum>() {
				public int compare(ChartTableEnum o1, ChartTableEnum o2) {
					return o1.getPriority() - o2.getPriority();
				}
			});
			for (int i = 0; i < chartTableList.size(); i++) {
				ChartTableEnum chartTableEnum = chartTableList.get(i);
				if (i == 0) {
					sb.append(chartTableEnum).append(" ").append(chartTableEnum.getAliasName());
				} else {
					sb.append(chartTableEnum.getLeftJoinSql());
				}
			}

		}
		return sb.toString();
	}

	private String loadDataParamsSql() {
		StringBuilder sb = new StringBuilder();
		sb.append(chartParameter).append(" AS data");
		return sb.toString();
	}

	private String loadgroupByParamsSql() {
		StringBuilder sb = new StringBuilder();
		if (chartGroupBys != null) {
			for (int i = 0; i < chartGroupBys.size(); i++) {
				ChartGroupByEnum chartGroupByEnum = chartGroupBys.get(i);
				sb.append(chartGroupByEnum).append(" AS label").append(i + 1).append(",");
			}
		}
		return sb.toString();
	}

	public void setCondition(String chartConditionType, Object value) {
		ChartConditionEnum chartConditionEnum = ChartConditionEnum.getChartConditionEnum(chartConditionType);
		this.chartConditions.put(chartConditionEnum, value);
		ChartTableEnum[] chartTableEnums = ChartConditionEnum.getChartTableEnum(chartConditionEnum);
		addChartTableEnums(chartTableEnums);
	}

	public void setParameter(int chartParamertType) {
		ChartParameterEnum chartParameterEnum = ChartParameterEnum.getChartParameterEnum(chartParamertType);
		this.chartParameter = chartParameterEnum;
		ChartTableEnum[] chartTableEnums = ChartParameterEnum.getChartTableEnum(chartParameterEnum);
		addChartTableEnums(chartTableEnums);
	}

	public void setGroupBy(int chartGroupByType) {
		ChartGroupByEnum chartGroupByEnum = ChartGroupByEnum.getChartGroupByEnum(chartGroupByType);
		this.chartGroupBys.add(chartGroupByEnum);
		ChartTableEnum[] chartTableEnums = ChartGroupByEnum.getChartTableEnum(chartGroupByEnum);
		addChartTableEnums(chartTableEnums);
	}

	private void addChartTableEnums(ChartTableEnum[] chartTableEnums) {
		if (chartTableEnums != null) {
			for (ChartTableEnum table : chartTableEnums) {
				this.chartTables.add(table);
			}
		}
	}

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		System.out.println(sb.toString());
		System.out.println(1);
	}
}
