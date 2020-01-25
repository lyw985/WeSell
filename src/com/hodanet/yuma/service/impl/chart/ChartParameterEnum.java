package com.hodanet.yuma.service.impl.chart;

public enum ChartParameterEnum {
	PARAMETER_SUM_ORDER_COUNT(11, "订单总量", "COUNT(DISTINCT yumaOrderItem.order_id) "),
	PARAMETER_SUM_ORDER_PRICE(12, "订单总价", "ROUND(SUM(yumaOrderItem.pay_price * yumaOrderItem.count),1)"),
	PARAMETER_AVG_ORDER_PRICE(13, "订单均价",
			"ROUND(SUM(yumaOrderItem.pay_price * yumaOrderItem.count)/COUNT(DISTINCT yumaOrderItem.order_id),1) "),
	PARAMETER_SUM_ORDER_ITEM_COUNT(14, "订单商品数量", "ROUND(SUM(yumaOrderItem.count),1) "),

	PARAMETER_SUM_ITEM_COUNT(21, "商品总量", "SUM(yumaOrderItem.COUNT * yumaWeidianItemModelMapping.COUNT) "),
	PARAMETER_SUM_ITEM_PRICE(22, "商品总价",
			"ROUND(SUM(yumaOrderItem.COUNT * yumaOrderItem.pay_price * yumaWeidianItemModelMapping.price_percent),2) "),
	PARAMETER_AVG_ITEM_PRICE(23, "商品均价",
			"ROUND(SUM(yumaOrderItem.COUNT * yumaOrderItem.pay_price * yumaWeidianItemModelMapping.price_percent)/SUM(yumaOrderItem.COUNT * yumaWeidianItemModelMapping.COUNT),2) "),

	PARAMETER_SUM_CUSTOMER_COUNT(31, "客户数量", "COUNT(DISTINCT yumaReceiver.phone) ");

	private int type;
	private String tip;
	private String sql;

	private ChartParameterEnum(int type, String tip, String sql) {
		this.type = type;
		this.tip = tip;
		this.sql = sql;
	}

	public String getTip() {
		return tip;
	}

	public int getType() {
		return type;
	}

	public String toString() {
		return sql;
	}

	public static ChartTableEnum[] getChartTableEnum(ChartParameterEnum chartParameterEnum) {
		switch (chartParameterEnum) {
		case PARAMETER_SUM_ORDER_COUNT:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case PARAMETER_SUM_ORDER_PRICE:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case PARAMETER_AVG_ORDER_PRICE:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case PARAMETER_SUM_ORDER_ITEM_COUNT:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM };
		case PARAMETER_SUM_ITEM_COUNT:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM,
					ChartTableEnum.TABLE_WEIDIAN_IMTE_MODEL_MAPPING, ChartTableEnum.TABLE_ITEM_MODEL,
					ChartTableEnum.TABLE_ITEM };
		case PARAMETER_SUM_ITEM_PRICE:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM,
					ChartTableEnum.TABLE_WEIDIAN_IMTE_MODEL_MAPPING, ChartTableEnum.TABLE_ITEM_MODEL,
					ChartTableEnum.TABLE_ITEM };
		case PARAMETER_AVG_ITEM_PRICE:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM,
					ChartTableEnum.TABLE_WEIDIAN_IMTE_MODEL_MAPPING, ChartTableEnum.TABLE_ITEM_MODEL,
					ChartTableEnum.TABLE_ITEM };
		case PARAMETER_SUM_CUSTOMER_COUNT:
			return new ChartTableEnum[] { ChartTableEnum.TABLE_ORDER_ITEM, ChartTableEnum.TABLE_RECEIVER };
		}
		return null;
	}

	public static ChartParameterEnum getChartParameterEnum(int type) {
		switch (type) {
		case 11:
			return PARAMETER_SUM_ORDER_COUNT;
		case 12:
			return PARAMETER_SUM_ORDER_PRICE;
		case 13:
			return PARAMETER_AVG_ORDER_PRICE;
		case 14:
			return PARAMETER_SUM_ORDER_ITEM_COUNT;
		case 21:
			return PARAMETER_SUM_ITEM_COUNT;
		case 22:
			return PARAMETER_SUM_ITEM_PRICE;
		case 23:
			return PARAMETER_AVG_ITEM_PRICE;
		case 31:
			return PARAMETER_SUM_CUSTOMER_COUNT;
		}
		return null;
	}
}
