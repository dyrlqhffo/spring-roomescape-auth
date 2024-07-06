package roomescape.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.auth.AuthCheckResponse;
import roomescape.dto.auth.AuthLoginRequest;
import roomescape.exception.ErrorCode;
import roomescape.exception.custom.AuthorizationException;
import roomescape.service.AuthService;

import java.util.Arrays;

@RestController
@RequestMapping("/login")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private static final String TOKEN_COOKIE_NAME = "token";
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Void> authLogin(@RequestBody AuthLoginRequest request,
                                                       HttpServletResponse response) {
        String token = authService.authLogin(request);
        Cookie cookie = createCookie(token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/check")
    public ResponseEntity<AuthCheckResponse> checkLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String accessToken = extractTokenFromCookie(cookies);
        checkAccessToken(accessToken);
        AuthCheckResponse userResponse = authService.findUserFromToken(accessToken);
        return ResponseEntity.ok(userResponse);

    }

    private String extractTokenFromCookie(Cookie[] cookies) {

        return Arrays.stream(cookies)
                .filter(cookie -> TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("");
    }

    private void checkAccessToken(String accessToken) {
        if (accessToken.isEmpty()) {
            throw new AuthorizationException(ErrorCode.UNAUTHORIZED_USER, "다시 로그인해주세요.");
        }
    }


    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }


}
