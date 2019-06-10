package com.isoft.system.exception;

/**
 * 自定义业务异常
 */
public class BusinessException extends RuntimeException {

    /**
     * Creates a new BusinessException.
     */
    public BusinessException() {super(); }

    /**
     * Constructs a new BusinessException.
     *
     * @param message the reason for the exception
     */
    public BusinessException(String message) {super(message); }

    /**
     * Constructs a new BusinessException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new BusinessException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
