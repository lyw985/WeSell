package com.hodanet.common.constant;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @anthor Administrator
 * @version 2016-11-15 11:05:00
 */
@Component
public class CommonConstants {

	private final static Logger logger = Logger.getLogger(CommonConstants.class);

	public final static String ADDRESS_TOP_PARENT = "0";

	public static int IMPORT_FILE_FLAG = 0;
	
	public static int SYSC_WEIDIAN_DATA_LOCK = 0;

	public static int SYSC_RECEIVER_DATA_LOCK = 0;
	
	public static int SYSC_VERIFY_LOCK = 0;
	/**
	 * 1.eX0d4EPG7wrIDjTKjQ9MYgSBhUwdw43v
	 * 2.disgVtFX4iDBQvDMeLGCFikyWzK36Nhz
	 */
	public static String API_BAIDU_AK="eX0d4EPG7wrIDjTKjQ9MYgSBhUwdw43v";
	
	public static String API_BAIDU_GET_LOCATION_URL="http://api.map.baidu.com/place/v2/search?";
	
//	private static AddressService addressService;

//    public static List<Address>   addressList        = null;
//
//    public static List<Address> getAddressList() {
//        if (addressList == null) {
//            if (addressService == null) {
//                addressService = SpringContextUtil.getBean(AddressServiceImpl.class);
//                if (addressService == null) {
//                    return null;
//                }
//            }
//            addressList = addressService.getAddressByParentId("0");
//        }
//        return addressList;
//    }
}
