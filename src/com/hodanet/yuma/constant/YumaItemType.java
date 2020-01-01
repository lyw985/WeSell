package com.hodanet.yuma.constant;

public enum YumaItemType {
	WEIGHT_JIN(0, "斤"), NUMBER_TIAO(1, "条"), NUMBER_ZHI(2, "只"), NUMBER_PING(3, "瓶"),NUMBER_GE(4, "个");

	private int status;
	private String tip;

	private YumaItemType(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static YumaItemType getYumaItemType(int type) {
		switch (type) {
		case 0:
			return WEIGHT_JIN;
		case 1:
			return NUMBER_TIAO;
		case 2:
			return NUMBER_ZHI;
		case 3:
			return NUMBER_PING;
		case 4:
			return NUMBER_GE;
		}
		return null;
	}
}
