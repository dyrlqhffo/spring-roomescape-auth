package roomescape.domain;

import roomescape.exception.ErrorCode;
import roomescape.exception.custom.AuthorizationException;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new AuthorizationException(ErrorCode.UNAUTHORIZED_USER, "패스워드가 일치하지 않습니다. 다시 입력해주세요.");
        }
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
