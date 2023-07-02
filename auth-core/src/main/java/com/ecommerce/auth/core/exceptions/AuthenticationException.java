package com.ecommerce.auth.core.exceptions;

/**
 * 鉴权异常
 */
public class AuthenticationException extends Exception{
    public AuthenticationException(){
        super();
    }

    public AuthenticationException(String errorMsg){
        super(errorMsg);
    }

    public AuthenticationException(String errorMsg, Throwable e){
        super(errorMsg,e);
    }
}
