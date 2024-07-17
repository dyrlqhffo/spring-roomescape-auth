package roomescape.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Role;
import roomescape.domain.User;
import roomescape.dto.auth.AuthCheckResponse;
import roomescape.dto.auth.AuthLoginRequest;
import roomescape.exception.ErrorCode;
import roomescape.exception.custom.UserNotFoundException;
import roomescape.jwt.JwtTokenProvider;
import roomescape.repository.AuthRepository;

@Service
@Transactional
public class AuthService {

    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(AuthRepository authRepository, JwtTokenProvider jwtTokenProvider) {
        this.authRepository = authRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String authLogin(AuthLoginRequest request) {
        User user = authRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND,"존재하지 않는 회원입니다. 다시 입력해주세요."));
        user.checkPassword(request.getPassword());
        return jwtTokenProvider.createToken(request.getEmail());

    }

    public User findUserFromToken(String accessToken) {
        String email = jwtTokenProvider.getEmailByToken(accessToken);
        User user = authRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND, "유저가 존재하지 않습니다."));
        return new User(user.getName(), user.getEmail(), user.getPassword(), Role.USER);
    }

    public AuthCheckResponse checkUser(User user) {
        user.checkUser(user);
        return new AuthCheckResponse(user.getName());
    }
}