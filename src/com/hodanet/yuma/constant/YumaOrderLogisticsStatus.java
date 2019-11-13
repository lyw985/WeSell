package com.hodanet.yuma.constant;

public enum YumaOrderLogisticsStatus {
	UNSEND(0,"未发货"), SENDED(1, "已发货"), DELIVERED(2, "已派送");

	private int status;
	private String tip;

	private YumaOrderLogisticsStatus(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static YumaOrderLogisticsStatus getYumaOrderLogisticsStatus(
			int status) {
		switch (status) {
		case 0:
			return UNSEND;
		case 1:
			return SENDED;
		case 2:
			return DELIVERED;
		}
		return null;
	}
}
