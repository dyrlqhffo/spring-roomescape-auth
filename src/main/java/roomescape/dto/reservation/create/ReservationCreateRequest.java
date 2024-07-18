package roomescape.dto.reservation.create;

import jakarta.validation.constraints.*;
import roomescape.domain.User;

public class ReservationCreateRequest {

    @NotBlank(message = "날짜를 제대로 입력해주세요.")
    private String date;
//    @NotBlank(message = "예약자는 필수항목입니다.")
    private String name;

    @NotNull(message = "시간을 선택해주세요.")
    private Long timeId;

    @NotNull(message = "테마을 선택해주세요.")
    private Long themeId;

    public void addName(String userName) {
        this.name = userName;
    }

    public ReservationCreateRequest() {
    }

    public ReservationCreateRequest(String date, String name, Long timeId, Long themeId) {
        this.date = date;
        this.name = name;
        this.timeId = timeId;
        this.themeId = themeId;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getTimeId() {
        return timeId;
    }

    public Long getThemeId() {
        return themeId;
    }
}
