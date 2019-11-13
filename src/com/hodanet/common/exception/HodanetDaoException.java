package com.hodanet.common.exception;

/**
 * @author lance.lengcs
 * @version 2012-7-23 1:25:25
 * 
 * <pre>
 *    ݿ⴦쳣װ.
 * </pre>
 */
public class HodanetDaoException extends RuntimeException {

    /**
     * ĬserialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ݿ⴦쳣.
     */
    public HodanetDaoException(){
        super();
    }

    /**
     * ݿ⴦쳣.
     * 
     * @param message Ϣ
     * @param cause 쳣
     */
    public HodanetDaoException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * ݿ⴦쳣.
     * 
     * @param message Ϣ
     */
    public HodanetDaoException(String message){
        super(message);
    }

    /**
     * ݿ⴦쳣.
     * 
     * @param cause 쳣
     */
    public HodanetDaoException(Throwable cause){
        super(cause);
    }

}
