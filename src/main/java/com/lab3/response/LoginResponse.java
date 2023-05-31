package com.lab3.response;

import lombok.Getter;
import lombok.Setter;

public class LoginResponse {
    @Getter
    @Setter
    boolean ok;
    @Getter
    @Setter
    String message;
    public LoginResponse(boolean ok, String message)
    {
        this.ok = ok;
        this.message = message;
    }
}
