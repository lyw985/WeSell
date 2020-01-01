package com.hodanet.yuma.service.impl.chart;

public enum ChartConditionEnum {
	CONDITION_ORDER_ITEM_NOT_EQUAL("orderItemStatusNotEqual", " yumaOrderItem.status != ? "),
	
	CONDITION_PAY_TIME_LARGER("startDate", " DATE(yumaOrderItem.pay_time) >=? "),
	CONDITION_PAY_TIME_LESS("endDate", " DATE(yumaOrderItem.pay_time) <=? "),

	CONDITION_ITEM_EQUAL("item_id", " yumaItem.id = ? "),
	CONDITION_ITEM_MODEL_EQUAL("item_model_id", " yumaItemModel.id = ? "),

	CONDITION_PROVINCE_EQUAL("province_id", " pro.id = ? "), CONDITION_CITY_EQUAL("city_id", " cit.id = ? "),
	CONDITION_AREA_EQUAL("area_id", " are.id = ? ");

	private String type;
	private String sql;

	private ChartConditionEnum(String type, String sql) {
		this.type = type;
		this.sql = sql;
	}

	public String toString() {
		return sql;
	}

	public static ChartTableEnum[] getChartTableEnum(ChartConditionEnum chartConditionEnum) {
		switch (chartConditionEnum) {
		case CONDITION_ORDER_ITEM_NOT_EQUAL:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case CONDITION_PAY_TIME_LARGER:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case CONDITION_PAY_TIME_LESS:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case CONDITION_PROVINCE_EQUAL:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM, ChartTableEnum.TABLE_RECEIVER,
					ChartTableEnum.TABLE_PROVINCE };
		case CONDITION_CITY_EQUAL:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM, ChartTableEnum.TABLE_RECEIVER,
					ChartTableEnum.TABLE_PROVINCE, ChartTableEnum.TABLE_CITY };
		case CONDITION_AREA_EQUAL:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM, ChartTableEnum.TABLE_RECEIVER,
					ChartTableEnum.TABLE_PROVINCE, ChartTableEnum.TABLE_CITY, ChartTableEnum.TABLE_AREA };
		case CONDITION_ITEM_EQUAL:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM,
					ChartTableEnum.TABLE_WEIDIAN_IMTE_MODEL_MAPPING, ChartTableEnum.TABLE_ITEM_MODEL,
					ChartTableEnum.TABLE_ITEM };
		case CONDITION_ITEM_MODEL_EQUAL:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM,
					ChartTableEnum.TABLE_WEIDIAN_IMTE_MODEL_MAPPING, ChartTableEnum.TABLE_ITEM_MODEL,
					ChartTableEnum.TABLE_ITEM };
		}
		return null;
	}

	public static ChartConditionEnum getChartConditionEnum(String type) {
		if ("orderItemStatusNotEqual".equals(type)) {
			return CONDITION_ORDER_ITEM_NOT_EQUAL;
		}
		if ("startDate".equals(type)) {
			return CONDITION_PAY_TIME_LARGER;
		}
		if ("endDate".equals(type)) {
			return CONDITION_PAY_TIME_LESS;
		}
		if ("item_id".equals(type)) {
			return CONDITION_ITEM_EQUAL;
		}
		if ("item_model_id".equals(type)) {
			return CONDITION_ITEM_MODEL_EQUAL;
		}
		if ("province_id".equals(type)) {
			return CONDITION_PROVINCE_EQUAL;
		}
		if ("city_id".equals(type)) {
			return CONDITION_CITY_EQUAL;
		}
		if ("area_id".equals(type)) {
			return CONDITION_AREA_EQUAL;
		}
		return null;
	}
}
