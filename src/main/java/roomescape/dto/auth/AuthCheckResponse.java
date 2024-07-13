package roomescape.dto.auth;

public class AuthCheckResponse {

    private String name;

    public AuthCheckResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
