package com.hodanet.yuma.service.impl.chart;

public enum ChartTableEnum {
	TABLE_ORDER_ITEM(0, "yuma_order_item", "yumaOrderItem", ""),
	TABLE_RECEIVER(1, "yuma_receiver", "yumaReceiver",
			" LEFT JOIN yuma_receiver yumaReceiver on yumaReceiver.id = yumaOrderItem.receiver_id "),
	TABLE_PROVINCE(2, "province", "pro", " LEFT JOIN province pro on pro.id = yumaReceiver.province_id "),
	TABLE_CITY(2, "city", "cit", " LEFT JOIN city cit on cit.id = yumaReceiver.city_id "),
	TABLE_AREA(2, "area", "are", " LEFT JOIN area are on are.id = yumaReceiver.area_id "),
	TABLE_WEIDIAN_IMTE_MODEL_MAPPING(1, "yuma_weidian_item_model_mapping", "yumaWeidianItemModelMapping",
			" LEFT JOIN yuma_weidian_item_model_mapping yumaWeidianItemModelMapping ON yumaWeidianItemModelMapping.weidian_item_model_id = yumaOrderItem.weidian_item_model_id "),
	TABLE_ITEM_MODEL(2, "yuma_item_model", "yumaItemModel",
			" LEFT JOIN yuma_item_model yumaItemModel ON yumaItemModel.id = yumaWeidianItemModelMapping.item_model_id "),
	TABLE_ITEM(3, "yuma_item", "yumaItem", " LEFT JOIN yuma_item yumaItem ON yumaItem.id = yumaItemModel.item_id ");

	private int priority;
	private String tableName;
	private String aliasName;
	private String leftJoinSql;

	private ChartTableEnum(int priority, String tableName, String aliasName, String leftJoinSql) {
		this.priority = priority;
		this.aliasName = aliasName;
		this.tableName = tableName;
		this.leftJoinSql = leftJoinSql;
	}

	public int getPriority() {
		return priority;
	}

	public String toString() {
		return tableName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public String getLeftJoinSql() {
		return leftJoinSql;
	}
}
