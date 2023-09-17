package com.flightbookingsystem.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class UserIdentification implements java.io.Serializable {
    @Id
    @Column(name = "username")
    @Email(regexp = ".+[@].+[\\.].+", message = "Invalid email format!")
    protected String username;

    @Id
    @Column(name = "password")
    @Pattern(regexp = "^(?=.[a-z])(?=.[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "The password must contain at least 1 uppercase letter, 1 lowercase letter and 1 digit!")
    @Size(min = 8, max = 64, message = "The password must contain at least 8 characters!")
    protected String password;
}
