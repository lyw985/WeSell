package com.hodanet.yuma.constant;

public enum SyncStatus {
	INIT(0, "初始化"), SYNC_ING(1, "同步中"), SYNC_SUCCESS(2, "同步成功"), SYNC_FAILED(3, "同步失败");

	private int status;
	private String tip;

	private SyncStatus(int status, String tip) {
		this.status = status;
		this.tip = tip;
	}

	public int getValue() {
		return status;
	}

	public String toString() {
		return tip;
	}

	public static SyncStatus getSyncStatus(int status) {
		switch (status) {
		case 0:
			return INIT;
		case 1:
			return SYNC_ING;
		case 2:
			return SYNC_SUCCESS;
		case 3:
			return SYNC_FAILED;
		}
		return null;
	}
}
