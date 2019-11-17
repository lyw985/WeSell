package com.hodanet.yuma.constant;

public enum YumaItemType {
	WEIGHT_JIN(0, "斤"), NUMBER_TIAO(1, "条");

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
		}
		return null;
	}
}
