package com.jwj.mvc2.authentication.web.login;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
