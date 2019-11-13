package com.hodanet.yuma.constant;

public enum YumaOrderStatus {
	WAITFORSEND(0, "等待发货"), SENDED(1, "已发货"), DELIVERED(2, "派送中"), SUCCESS(3,
			"派送成功"), CLOSE(9, "派送失败");

	private int status;
	private String tip;

	private YumaOrderStatus(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static YumaOrderStatus getYumaOrderStatus(int status) {
		switch (status) {
		case 0:
			return WAITFORSEND;
		case 1:
			return SENDED;
		case 2:
			return DELIVERED;
		case 3:
			return SUCCESS;
		case 9:
			return CLOSE;
		}
		return null;
	}
}
