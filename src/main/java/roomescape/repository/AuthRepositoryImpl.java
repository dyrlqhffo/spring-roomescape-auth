package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.User;

import java.util.Optional;
@Repository
public class AuthRepositoryImpl implements AuthRepository{

    private final JdbcTemplate jdbcTemplate;

    public AuthRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        String sql = "select name, email, password from users where email = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(
                sql, (rs, rowNum) -> new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
        ), email));
    }

    @Override
    public Optional<User> findById(Long userId) {
        String sql = "select name, email, password from users where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                sql, (rs, rowNum) -> new User(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                ), userId));
    }
}
