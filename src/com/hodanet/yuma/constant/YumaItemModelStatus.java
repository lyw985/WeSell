package com.hodanet.yuma.constant;

public enum YumaItemModelStatus {
	INAVAILABLE(0, "禁用"), AVAILABLE(1, "可用");

	private int status;
	private String tip;

	private YumaItemModelStatus(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static YumaItemModelStatus getYumaItemModelStatus(int status) {
		switch (status) {
		case 0:
			return INAVAILABLE;
		case 1:
			return AVAILABLE;
		}
		return null;
	}
}
