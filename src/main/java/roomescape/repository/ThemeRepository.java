package roomescape.repository;

import roomescape.domain.Theme;
import roomescape.dto.theme.create.ThemeCreateRequest;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository {
    List<Theme> findThemes();

    Theme createTheme(Theme entity);

    void deleteTheme(Long id);

    Optional<Theme> findThemeById(Long themeId);

    int countDuplicatedName(ThemeCreateRequest request);
}
