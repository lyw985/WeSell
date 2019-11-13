package com.hodanet.common.entity.vo;

/**
 * @author lance.lengcs
 * @version 2012-8-1 2:51:20
 * 
 * <pre>
 * JSONϢ
 * </pre>
 */
public class JsonMessage {

    /** ״̬. */
    private boolean flag = true;
    /** Ϣ. */
    private String  msg  = "";

    public JsonMessage(){
    }

    /**
     * @param flag ״̬
     * @param msg 
     */
    public JsonMessage(boolean flag, String msg){
        this.flag = flag;
        this.msg = msg;
    }

    /**
     * @return the flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
