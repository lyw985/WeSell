package com.hodanet.system.util;

/**
 * CryptTool װһЩܹ߷,  3DES, MD5 .
 * 
 * @author hxq
 * @version 1.0 2006-01-10
 */
public class CryptTool {

    public CryptTool(){
    }

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
            "E", "F"                       };

    /**
     * תֽΪ16ִ
     * 
     * @param b ֽ
     * @return 16ִ
     */

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5 ժҪ(byte[]).
     * 
     * @param src byte[]
     * @throws Exception
     * @return byte[] 16 bit digest
     */
    public static byte[] md5Digest(byte[] src) throws Exception {
        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5"); // MD5 is 16 bit message
                                                                                          // digest

        return alg.digest(src);
    }

    /**
     * MD5 ժҪ(String).
     * 
     * @param src String
     * @throws Exception
     * @return String
     */
    public static String md5Digest(String src) throws Exception {
        return byteArrayToHexString(md5Digest(src.getBytes()));
    }

    /** Test crypt */
    public static void main(String[] args) {
        try {
            // õ
            String desStr = " MERCHANTID=123456789&ORDERSEQ=20060314000001&ORDERDATE=20060314&ORDERAMOUNT=10000";
            System.out.println("ԭַ desStr  " + desStr);
            // MAC
            String MAC = md5Digest(desStr);
            System.out.println("MAC == " + MAC);

            // ʹkeyֵ SIGN
            String keyStr = "123456";// ʹù̶key
            // õ
            desStr = "UPTRANSEQ=20080101000001&MERCHANTID=0250000001&ORDERID=2006050112564931556&PAYMENT=10000&RETNCODE=00&RETNINFO=00&PAYDATE =20060101";
            // keyֵ֯һǩĴ
            desStr = desStr + "&KEY=" + keyStr;
            System.out.println("ԭַ desStr  " + desStr);
            //  SIGN
            String SIGN = md5Digest(desStr);
            System.out.println("SIGN == " + SIGN);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
