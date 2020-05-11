package javaCode.Exception;

import java.io.IOException;

public class ReadOrdersException extends IOException {
    public ReadOrdersException(String message) {
        super(message);
    }
}
