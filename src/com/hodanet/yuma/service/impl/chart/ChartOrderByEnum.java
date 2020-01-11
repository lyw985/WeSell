package com.hodanet.yuma.service.impl.chart;

public enum ChartOrderByEnum {
	ORDERBY_NUMBER_DESC(11, " desc"), ORDERBY_NUMBER_ASC(12, " asc");

	private int type;
	private String sql;

	private ChartOrderByEnum(int type, String sql) {
		this.type = type;
		this.sql = sql;
	}

	public int getType() {
		return type;
	}

	public String toString() {
		return sql;
	}

	public static ChartOrderByEnum getChartOrderByEnum(int type) {
		switch (type) {
		case 11:
			return ORDERBY_NUMBER_DESC;
		case 12:
			return ORDERBY_NUMBER_ASC;
		}
		return null;
	}
}
