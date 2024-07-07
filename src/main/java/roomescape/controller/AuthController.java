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
import roomescape.util.CookieUtil;

@RestController
@RequestMapping("/login")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Void> authLogin(@RequestBody AuthLoginRequest request,
                                                       HttpServletResponse response) {
        String token = authService.authLogin(request);
        Cookie cookie = CookieUtil.createCookie(token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/check")
    public ResponseEntity<AuthCheckResponse> checkLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String accessToken = CookieUtil.extractTokenFromCookie(cookies)
                .orElseThrow(() -> new AuthorizationException(ErrorCode.UNAUTHORIZED_USER, "다시 로그인 해주세요."));
        AuthCheckResponse userResponse = authService.findUserFromToken(accessToken);
        return ResponseEntity.ok(userResponse);

    }

}
