package com.hodanet.common.constant;

public enum DisplayStatus {
	DISPLAY(0, "显示"), HIDDEN(1, "隐藏");

	private int status;
	private String tip;

	private DisplayStatus(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static DisplayStatus getDisplayStatus(int status) {
		switch (status) {
		case 0:
			return DISPLAY;
		case 1:
			return HIDDEN;
		}
		return null;
	}
}
