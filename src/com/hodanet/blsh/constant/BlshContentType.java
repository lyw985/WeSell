package com.hodanet.blsh.constant;

public enum BlshContentType {
    YULE_XINWENTOUTIAO(1, "娱乐_新闻头条"), YULE_LVYOU(2, "娱乐_旅游"), YULE_DIANYING(3, "娱乐_电影"), YULE_MEISHI(4, "娱乐_美食"),
    YULE_QITA(5, "娱乐_其他"), YOUHUIZHEKOU(6, "优惠折扣");

    private int    type;
    private String tip;

    private BlshContentType(int type, String tip){
        this.type = type;
        this.tip = tip;
    }

    public int getType() {
        return type;
    }

    public String getTip() {
        return tip;
    }

    public static BlshContentType getBlshContentType(int type) {
        switch (type) {
            case 1:
                return YULE_XINWENTOUTIAO;
            case 2:
                return YULE_LVYOU;
            case 3:
                return YULE_DIANYING;
            case 4:
                return YULE_MEISHI;
            case 5:
                return YULE_QITA;
            case 6:
                return YOUHUIZHEKOU;
        }
        return null;
    }
}
