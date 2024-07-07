package roomescape.repository;

import roomescape.domain.ReservationTime;
import roomescape.dto.time.ReservationTimeRequest;
import roomescape.dto.time.create.ReservationTimeCreateResponse;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    List<ReservationTime> findTimes();

    ReservationTime createTime(ReservationTime reservationTime);

    ReservationTime findReservationTimeById(Long id);

    void deleteTime(Long id);

    int countReservationTimeByStartAt(ReservationTimeRequest request);
}
