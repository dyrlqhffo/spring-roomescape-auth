package roomescape.repository;

import roomescape.domain.User;

import java.util.Optional;

public interface AuthRepository {

    Optional<User> findUserByEmail(String email);

    Optional<User> findById(Long userId);
}