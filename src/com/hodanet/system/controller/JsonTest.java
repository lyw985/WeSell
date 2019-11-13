package com.hodanet.system.controller;

import com.alibaba.fastjson.JSON;
import com.hodanet.system.entity.po.User;

/**
 * @author lance.lengcs
 * @version 2013-4-9 10:22:15
 * 
 * <pre>
 * 
 * </pre>
 */
public class JsonTest {

    public static void main(String[] args) {
        User user = new User();
        user.setId("abcdefg");
        user.setName("");
        user.setEmail("lance.lengcs@gmail.com");
        user.setMobile("13989816092");
        String aa = JSON.toJSONString(user);
        System.out.println(aa);

        User user2 = JSON.parseObject(aa, User.class);
        System.out.println(user2.getName());
    }

}
