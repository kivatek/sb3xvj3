package com.kivatek.sb3xvj3.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String roles;
    private boolean locked;
    private boolean expired;
    private boolean credentialsExpired;
    private String created_by;
    private Date created_at;
    private String updated_by;
    private Date updated_at;
    private boolean deleted;
}
