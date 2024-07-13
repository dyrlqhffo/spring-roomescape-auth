package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.theme.ThemeResponse;
import roomescape.dto.theme.create.ThemeCreateRequest;
import roomescape.dto.theme.create.ThemeCreateResponse;
import roomescape.exception.custom.DuplicatedThemeNameException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class ThemeServiceTest {


    @Autowired
    ThemeService themeService;

    @BeforeEach
    void init() {
       // themeService.createTheme(new ThemeCreateRequest("테마1", "테마1의 설명은 비밀입니다.", "http://"));
    }

    @Test
    @DisplayName("테마의 리스트 테스트")
    void findThemes() {
        //given
        ThemeCreateRequest request = new ThemeCreateRequest("hello",
                "첫번째 테마입니다.Hello 첫번째 테마입니다.Hello 첫번째 테마입니다.Hello", "테마이미지");
        themeService.createTheme(request);

        //when
        List<ThemeResponse> themes = themeService.findThemes();

        //then
        assertThat(themes).isNotEmpty();
        assertThat(themes).hasSize(1);
    }

    @Test
    @DisplayName("테마 생성")
    void create() {

        //given
        ThemeCreateRequest request = new ThemeCreateRequest("hello",
                "첫번째 테마입니다.Hello 첫번째 테마입니다.Hello 첫번째 테마입니다.Hello", "테마이미지");

        //when
        ThemeCreateResponse response = themeService.createTheme(request);

        //then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("hello");
        assertThat(response.getDescription()).isEqualTo(request.getDescription());
        assertThat(response.getThumbnail()).isEqualTo(request.getThumbnail());
    }

    @Test
    @DisplayName("테마 삭제")
    void delete() {
        //given
        ThemeCreateRequest request = new ThemeCreateRequest("hello", "첫번째 테마입니다.Hello 첫번째 테마입니다.Hello 첫번째 테마입니다.Hello", "테마이미지");
        ThemeCreateResponse response = themeService.createTheme(request);

        assertThat(response.getId()).isEqualTo(1L);

        //when
        themeService.deleteTheme(1L);

        //then
        assertThat(themeService.findThemes()).hasSize(0);
        assertThat(themeService.findThemes()).isEmpty();
    }

    @Test
    @DisplayName("중복 테마 등록시 예외 발생")
    void checkDuplicatedThemeName() {
        ThemeCreateRequest request = new ThemeCreateRequest("테마1", "첫번째 테마입니다.Hello 첫번째 테마입니다.Hello 첫번째 테마입니다.Hello", "테마이미지");
        ThemeCreateResponse response = themeService.createTheme(request);
        Assertions.assertThrows(DuplicatedThemeNameException.class, () -> {
            themeService.createTheme(new ThemeCreateRequest("테마1", "첫번째 테마입니다.Hello 첫번째 테마입니다.Hello 첫번째 테마입니다.Hello", "???"));
        });
    }

}