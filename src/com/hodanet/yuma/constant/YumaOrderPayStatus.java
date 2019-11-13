package com.hodanet.yuma.constant;

public enum YumaOrderPayStatus {
	UNPAY(0, "未支付"), PAYED(1, "已支付"), REFUNDING(2, "正在退款"), REFUNDED(3, "已退款");

	private int status;
	private String tip;

	private YumaOrderPayStatus(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static YumaOrderPayStatus getYumaOrderPayStatus(int status) {
		switch (status) {
		case 0:
			return UNPAY;
		case 1:
			return PAYED;
		}
		return null;
	}
}
