package roomescape.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class AuthLoginRequest {

    @NotBlank(message = "이메일을 다시 입력해주세요.")
    private String email;
    @NotBlank(message = "패스워드를 다시 입력해주세요.")
    private String password;

    public AuthLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
