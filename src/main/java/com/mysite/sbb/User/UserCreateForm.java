package com.mysite.sbb.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreateForm {
    @Size(min=3,max=25, message = "ID는 3글자 이상 25글자 이하 입니다.")
    @NotEmpty(message = "사용자ID는 필수 입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호확인은 필수 입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수 입니다.")
    @Email
    private String email;
}
