package com.hodanet.system.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.hodanet.common.util.StringUtil;

/**
 * @author lance.lengcs
 * @version 2012-7-27 2:10:22
 * 
 * <pre>
 * ܹ
 * </pre>
 */
public class SecurityUtil {

    /**
     * MD5
     * 
     * @param plaintext
     * @return
     */
    public static String getMD5(String plaintext) {

        if (StringUtil.isBlank(plaintext)) {
            return null;
        }

        return DigestUtils.md5Hex(plaintext);

    }

    public static void main(String args[]) {
        // System.out.println(getMD5("123456"));
        decodeBase64();
    }

    public static void decodeBase64() {
        // Base64.decodeFast("11111");
        String a = "NjE4MTQz";
        // System.out.println(Base64.decodeFast(a));
        System.out.println(Base64.decodeBase64(a.getBytes()));
    }
}
