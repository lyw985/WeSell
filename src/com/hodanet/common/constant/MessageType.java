package com.hodanet.common.constant;

public enum MessageType {
	SAVE_SUCCESS("新增或修改成功！"), SAVE_FAILED("新增或修改失败！"), DELETE_SUCCESS("删除成功！"), DELETE_FAILED("删除失败！"),
	UPDATE_SUCCESS("修改成功！"), UPDATE_FAILED("修改失败！"), GET_SUCCESS("获取成功！"), GET_FAILED("获取失败！");

	private String tip;

	private MessageType(String tip) {
		this.tip = tip;
	}

	public String toString() {
		return tip;
	}

}
