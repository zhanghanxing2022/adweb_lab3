package com.lab3.mybatis.po;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter
    @Setter
    private int userID;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phone;
    public User(int userID, String username, String password, String email, String
            phone) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
