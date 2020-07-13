package com.cts.fms.web.rest.errors;

public class FMSBusinessException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    /**
     * set the error code as got from FieldAccessControl or based on the scenario
     */
    private final String errorCode;

    /**
     * any arguments to be passed
     */
    private Object[] arguments;

    public FMSBusinessException(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public FMSBusinessException(String errorCode,String message) {
    	super(message);
        this.errorCode = errorCode;
    }

    public FMSBusinessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public FMSBusinessException(String errorCode, String message,
        Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public FMSBusinessException(String errorCode, Object[] arguments,
        Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    public FMSBusinessException(String errorCode, Object[] arguments) {
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    /**
     * return the error code
     * 
     * @return
     */
    public final String getErrorCode() {
        return errorCode;
    }

    /**
     * return arguments if any
     * 
     * @return
     */
    public final Object[] getArguments() {
        return arguments;
    }
}
