package com.hodanet.yuma.constant;

public enum YumaItemStatus {
	INAVAILABLE(0, "禁用"), AVAILABLE(1, "可用");

	private int status;
	private String tip;

	private YumaItemStatus(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static YumaItemStatus getYumaItemStatus(int status) {
		switch (status) {
		case 0:
			return INAVAILABLE;
		case 1:
			return AVAILABLE;
		}
		return null;
	}
}
