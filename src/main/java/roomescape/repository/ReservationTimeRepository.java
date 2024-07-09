package roomescape.repository;

import roomescape.domain.ReservationTime;
import roomescape.dto.time.ReservationTimeRequest;

import java.util.List;

public interface ReservationTimeRepository {

    List<ReservationTime> findTimes();

    ReservationTime createTime(ReservationTime reservationTime);

    ReservationTime findReservationTimeById(Long id);

    void deleteTime(Long id);

    int countReservationTimeByStartAt(String startAt);
}
