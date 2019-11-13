package com.hodanet.blsh.constant;

public enum BlshContentStatus {
    INIT(0, "Î´ÉóºË"), SUCCESS(1, "Í¨¹ıÉóºË"), FAIL(2, "Î´Í¨¹ıÉóºË") ;

    private int    status;
    private String tip;

    private BlshContentStatus(int status, String tip){
        this.status = status;
        this.tip = tip;
    }

    public int getValue() {
        return status;
    }

    public String toString() {
        return tip;
    }

    public static BlshContentStatus getBlshContentStatus(int status) {
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
