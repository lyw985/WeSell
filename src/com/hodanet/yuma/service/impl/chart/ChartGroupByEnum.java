package com.hodanet.yuma.service.impl.chart;

public enum ChartGroupByEnum {
	GROUPBY_YEAR(11, "DATE_FORMAT(yumaOrderItem.pay_time, '%Y')"),
	GROUPBY_YEAR_MONTH(12, " DATE_FORMAT(yumaOrderItem.pay_time, '%Y-%m') "),
	GROUPBY_YEAR_MONTH_DAY(13, "DATE_FORMAT(yumaOrderItem.pay_time, '%Y-%m-%d') "),
	GROUPBY_YEAR_WEEKS(14, "CONCAT(YEAR(yumaOrderItem.pay_time),'-',LPAD(WEEK(yumaOrderItem.pay_time)+1,2,'0')) "),
	GROUPBY_WEEK_MONDAY_TO_SUNDAY(15, "CONCAT('周',weekday(yumaOrderItem.pay_time)+1) "),
	GROUPBY_HOUR_0_TO_23(16,
			"CONCAT(LPAD(HOUR(yumaOrderItem.pay_time),2,'0'),':00-',LPAD(HOUR(yumaOrderItem.pay_time)+1,2,'0'),':00') "),
	GROUPBY_MONTH_1_TO_12(17,
			"CONCAT(DATE_FORMAT(yumaOrderItem.pay_time, '%m'),'月') "),

	GROUPBY_PROVINCE(21, "pro.name "), GROUPBY_CITY(22, "CONCAT(pro.name,'-',cit.name) "),
	GROUPBY_AREA(23, "CONCAT(pro.name,'-',cit.name,'-',are.name) "),

	GROUPBY_ITEM(31, "yumaItem.name "), GROUPBY_ITEM_MODEL(32, "CONCAT(yumaItem.name,'-',yumaItemModel.name) ");
	

	private int type;
	private String sql;

	private ChartGroupByEnum(int type, String sql) {
		this.type = type;
		this.sql = sql;
	}

	public int getType() {
		return type;
	}

	public String toString() {
		return sql;
	}

	public static ChartTableEnum[] getChartTableEnum(ChartGroupByEnum chartGroupByEnum) {
		switch (chartGroupByEnum) {
		case GROUPBY_YEAR:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case GROUPBY_YEAR_MONTH:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case GROUPBY_YEAR_MONTH_DAY:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case GROUPBY_YEAR_WEEKS:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case GROUPBY_WEEK_MONDAY_TO_SUNDAY:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case GROUPBY_HOUR_0_TO_23:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case GROUPBY_MONTH_1_TO_12:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case GROUPBY_PROVINCE:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM, ChartTableEnum.TABLE_RECEIVER,
					ChartTableEnum.TABLE_PROVINCE };
		case GROUPBY_CITY:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM, ChartTableEnum.TABLE_RECEIVER,
					ChartTableEnum.TABLE_PROVINCE, ChartTableEnum.TABLE_CITY };
		case GROUPBY_AREA:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM, ChartTableEnum.TABLE_RECEIVER,
					ChartTableEnum.TABLE_PROVINCE, ChartTableEnum.TABLE_CITY, ChartTableEnum.TABLE_AREA };
		case GROUPBY_ITEM:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM,
					ChartTableEnum.TABLE_WEIDIAN_IMTE_MODEL_MAPPING, ChartTableEnum.TABLE_ITEM_MODEL,
					ChartTableEnum.TABLE_ITEM };
		case GROUPBY_ITEM_MODEL:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM,
					ChartTableEnum.TABLE_WEIDIAN_IMTE_MODEL_MAPPING, ChartTableEnum.TABLE_ITEM_MODEL,
					ChartTableEnum.TABLE_ITEM };
		}
		return null;
	}

	public static ChartGroupByEnum getChartGroupByEnum(int type) {
		switch (type) {
		case 11:
			return GROUPBY_YEAR;
		case 12:
			return GROUPBY_YEAR_MONTH;
		case 13:
			return GROUPBY_YEAR_MONTH_DAY;
		case 14:
			return GROUPBY_YEAR_WEEKS;
		case 15:
			return GROUPBY_WEEK_MONDAY_TO_SUNDAY;
		case 16:
			return GROUPBY_HOUR_0_TO_23;
		case 17:
			return GROUPBY_MONTH_1_TO_12;
		case 21:
			return GROUPBY_PROVINCE;
		case 22:
			return GROUPBY_CITY;
		case 23:
			return GROUPBY_AREA;
		case 31:
			return GROUPBY_ITEM;
		case 32:
			return GROUPBY_ITEM_MODEL;
		}
		return null;
	}
}
