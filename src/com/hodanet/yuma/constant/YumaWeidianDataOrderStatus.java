package com.hodanet.yuma.constant;

public enum YumaWeidianDataOrderStatus {
	CLOSED(0, "已关闭");

	private int status;
	private String tip;

	private YumaWeidianDataOrderStatus(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static YumaWeidianDataOrderStatus getYumaWeidianDataOrderStatus(String tip) {
		if (CLOSED.toString().equals(tip)) {
			return CLOSED;
		}
		return null;
	}

	public static YumaWeidianDataOrderStatus getYumaWeidianDataOrderStatus(int status) {
		switch (status) {
		case 0:
			return CLOSED;
		}
		return null;
	}
}
