package roomescape.dto.auth;

public class AuthLoginResponse {

    private String accessToken;

    public AuthLoginResponse() {
    }

    public AuthLoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
