package com.hodanet.system.constant;

/**
 * @author lance.lengcs
 * @version 2012-7-24 4:51:11
 * 
 * <pre>
 *    û״̬־.
 * </pre>
 */
public enum UserStatus {

    ENABLE("enable"), // ״̬
    DISABLE("disabled"); // ͣ״̬

    private String status;

    private UserStatus(String status){
        this.status = status;
    }

    public String toString() {
        return status;
    }
}
