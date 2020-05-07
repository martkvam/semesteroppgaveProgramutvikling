package javaCode.Exception;

import java.io.IOException;

public class UserAlreadyExistException extends IOException {
    public UserAlreadyExistException(String msg){
        super(msg);
    }
}

