package roomescape.dto.theme.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import roomescape.domain.Theme;

public class ThemeCreateRequest {

    @NotBlank(message = "테마의 제목은 필수입니다.")
    private String name;
    @NotBlank(message = "테마의 설명은 필수입니다.")
    private String description;
    private String thumbnail;

    public ThemeCreateRequest(String name, String description, String thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
