package io.github.payment.domain;

import java.time.LocalDate;
import java.util.List;

public class User {

    private String id;

    private String login;

    private String password;

    private String email;

    private String name;

    private String lastName;

    private String phoneNumber;

    private String cpf;

    private List<String> roles;

    private String verificationCode;

    private boolean enabled;

    private LocalDate createdAt;

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCpf() {
        return cpf;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
