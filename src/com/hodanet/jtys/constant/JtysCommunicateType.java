package com.hodanet.jtys.constant;

public enum JtysCommunicateType {
    QUESTION(0, "Œ Ã‚"), ANSWER(1, "ªÿ¥");

    private int    type;
    private String tip;

    private JtysCommunicateType(int type, String tip){
        this.type = type;
        this.tip = tip;
    }

    public int getValue() {
        return type;
    }

    public String toString() {
        return tip;
    }

    public static JtysCommunicateType getJtysCommunicateType(int type) {
        switch (type) {
            case 0:
                return QUESTION;
            case 1:
                return ANSWER;
        }
        return null;
    }
}
