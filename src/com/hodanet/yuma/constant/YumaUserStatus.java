package com.hodanet.yuma.constant;

public enum YumaUserStatus {
    USER(0, "游客");

    private int    status;
    private String tip;

    private YumaUserStatus(int status, String tip){
        this.status = status;
        this.tip = tip;
    }

    public int getValue() {
        return status;
    }

    public String toString() {
        return tip;
    }

    public static YumaUserStatus getJtysUserStatus(int status) {
        switch (status) {
            case 0:
                return USER;
        }
        return null;
    }
}
