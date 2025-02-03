package io.github.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
