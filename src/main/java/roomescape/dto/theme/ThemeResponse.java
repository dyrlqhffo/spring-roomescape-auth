package roomescape.dto.theme;


import roomescape.domain.Theme;
import roomescape.dto.theme.create.ThemeCreateRequest;

import java.util.List;
import java.util.stream.Collectors;

public class ThemeResponse {

    private Long id;
    private String name;
    private String description;
    private String thumbnail;

    public static ThemeResponse fromDomain(Theme theme) {
        //설명이 길 경우 ...표시를 하기 때문에 테마 리스트를 보여줄 때 사용
        String foundDescription = theme.getDescription();
        if (foundDescription.length() > 10) {
            foundDescription = foundDescription.substring(0, 5) + "...";
        }
        return new ThemeResponse(theme.getId(), theme.getName(), foundDescription, theme.getThumbnail());
    }

    public ThemeResponse() {
    }

    public ThemeResponse(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Long getId() {
        return id;
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
