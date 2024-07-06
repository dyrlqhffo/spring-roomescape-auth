package roomescape.dto.time.available;

import roomescape.domain.ReservationTime;

public class ReservationTimeAvailableResponse {

    private Long id;
    private String startAt;

    public static ReservationTimeAvailableResponse fromReservationTime(ReservationTime reservationTime) {
        return new ReservationTimeAvailableResponse(reservationTime.getId(), reservationTime.getStartAt().toString());
    }

    public ReservationTimeAvailableResponse() {
    }

    public ReservationTimeAvailableResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
