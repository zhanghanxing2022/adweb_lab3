package com.lab3.request;

import lombok.Getter;
import lombok.Setter;

public class UserRegisterRequest {
    @Getter
    @Setter
    private String username, password,email, phone;

}
