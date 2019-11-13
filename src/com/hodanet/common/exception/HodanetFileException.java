package com.hodanet.common.exception;

/**
 * @author lance.lengcs
 * @version 2012-8-20 5:45:46
 * 
 * <pre>
 * ļ쳣
 * </pre>
 */
public class HodanetFileException extends RuntimeException {

    /**
     * ĬserialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ļ쳣.
     */
    public HodanetFileException(){
        super();
    }

    /**
     * ļ쳣.
     * 
     * @param message 쳣
     * @param cause 쳣
     */
    public HodanetFileException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * ļ쳣.
     * 
     * @param message 쳣
     */
    public HodanetFileException(String message){
        super(message);
    }

    /**
     * ļ쳣.
     * 
     * @param cause 쳣
     */
    public HodanetFileException(Throwable cause){
        super(cause);
    }

}
