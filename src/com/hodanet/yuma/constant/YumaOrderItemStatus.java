package com.hodanet.yuma.constant;

public enum YumaOrderItemStatus {
	NORMAL(0, "初始化"), FACKE(-1, "伪造");

	private int status;
	private String tip;

	private YumaOrderItemStatus(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static YumaOrderItemStatus getYumaOrderStatus(int status) {
		switch (status) {
		case 0:
			return NORMAL;
		case -1:
			return FACKE;
		}
		return null;
	}
}
