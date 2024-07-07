package roomescape.domain;

import roomescape.dto.theme.create.ThemeCreateRequest;
import roomescape.exception.ErrorCode;
import roomescape.exception.custom.InvalidInputException;

public class Theme {
    private Long id;
    private String name;
    private String description;
    private String thumbnail;

    public static Theme from(ThemeCreateRequest request) {
        return new Theme(request.getName(), request.getDescription(), request.getThumbnail());
    }

    private static void validateName(String name) {
        if(name.length() < 3 ||
                name.length() > 21){
            throw new InvalidInputException(ErrorCode.INVALID_INPUT,"제목은 3글자 이상 20글자 이하여야합니다.");
        }
    }

    private static void validateDescription(String description) {
        if(description.length() < 5){
            throw new InvalidInputException(ErrorCode.INVALID_INPUT,"설명이 너무 짧습니다. 더 작성해주세요.");
        }
    }

    public Theme(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Theme(String name, String description, String thumbnail) {
        validateName(name);
        validateDescription(description);
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Theme(Long id, String name, String description, String thumbnail) {
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
