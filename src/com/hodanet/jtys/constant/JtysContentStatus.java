package com.hodanet.jtys.constant;

public enum JtysContentStatus {
    INIT(0, "Œ¥…Û∫À"), SUCCESS(1, "Õ®π˝…Û∫À"), FAIL(2, "Œ¥Õ®π˝…Û∫À") ;

    private int    status;
    private String tip;

    private JtysContentStatus(int status, String tip){
        this.status = status;
        this.tip = tip;
    }

    public int getValue() {
        return status;
    }

    public String toString() {
        return tip;
    }

    public static JtysContentStatus getJtysContentStatus(int status) {
        switch (status) {
            case 0:
                return INIT;
            case 1:
                return SUCCESS;
            case 2:
                return FAIL;
        }
        return null;
    }
}
