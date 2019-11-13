package com.hodanet.blsh.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @anthor lyw
 * @version 2014-4-11 1:47:40
 */
public class BlshConstant {

    public static Map<String, BlshContentType> blshDispatchMap     = new HashMap<String, BlshContentType>();

    public final static String                 SUBSCRIBE_CONTENT   = "ӭı";
    public final static String                 UNSUBSCRIBE_CONTENT = "лıڴ´ټ";

    static {
        blshDispatchMap.put("YULE_XINWENTOUTIAO", BlshContentType.YULE_XINWENTOUTIAO);
        blshDispatchMap.put("YULE_LVYOU", BlshContentType.YULE_LVYOU);
        blshDispatchMap.put("YULE_DIANYING", BlshContentType.YULE_DIANYING);
        blshDispatchMap.put("YULE_MEISHI", BlshContentType.YULE_MEISHI);
        blshDispatchMap.put("YULE_QITA", BlshContentType.YULE_QITA);
        blshDispatchMap.put("YOUHUIZHEKOU", BlshContentType.YOUHUIZHEKOU);

        // blshDispatchMap.put("1", BlshContentType.YULE_XINWENTOUTIAO);
        // blshDispatchMap.put("2", BlshContentType.YULE_LVYOU);
        // blshDispatchMap.put("3", BlshContentType.YULE_DIANYING);
        // blshDispatchMap.put("4", BlshContentType.YULE_MEISHI);
        // blshDispatchMap.put("5", BlshContentType.YULE_QITA);
        // blshDispatchMap.put("6", BlshContentType.YOUHUIZHEKOU);

    }
}
