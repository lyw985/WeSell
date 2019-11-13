package com.hodanet.jtys.constant;

public enum JtysUserStatus {
    GUEST(0, "游客"), WEIXIN_USER(1, "微信包月用户"), CNET_USER(2, "短信包月用户");

    private int    status;
    private String tip;

    private JtysUserStatus(int status, String tip){
        this.status = status;
        this.tip = tip;
    }

    public int getValue() {
        return status;
    }

    public String toString() {
        return tip;
    }

    public static JtysUserStatus getJtysUserStatus(int status) {
        switch (status) {
            case 0:
                return GUEST;
            case 1:
                return WEIXIN_USER;
            case 2:
                return CNET_USER;
        }
        return null;
    }
}
