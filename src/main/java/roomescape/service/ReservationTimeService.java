package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;
import roomescape.dto.time.ReservationTimeRequest;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.dto.time.available.ReservationTimeAvailableResponse;
import roomescape.dto.time.create.ReservationTimeCreateResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.custom.DuplicatedReservationTimeException;
import roomescape.exception.custom.ThemeNotFoundException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.ThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationTimeService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ThemeRepository themeRepository;

    public ReservationTimeService(ReservationRepository reservationRepository,
                                  ReservationTimeRepository reservationTimeRepository,
                                  ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.themeRepository = themeRepository;
    }


    public List<ReservationTimeResponse> findTimes() {
        return reservationTimeRepository.findTimes()
                .stream()
                .map(ReservationTimeResponse::new)
                .collect(Collectors.toList());
    }

    public ReservationTimeCreateResponse createTime(ReservationTimeRequest request) {
        checkDuplicatedReservationTime(request);
        Long id = reservationTimeRepository.createTime(request);
        ReservationTime entity = reservationTimeRepository.findReservationTimeById(id);
        return ReservationTimeCreateResponse.toDto(entity);
    }

    public void deleteTime(Long id) {
        reservationTimeRepository.deleteTime(id);
    }

    public void checkDuplicatedReservationTime(ReservationTimeRequest request) {
        int count = reservationTimeRepository.countReservationTimeByStartAt(request);
        if (count > 0) {
            throw new DuplicatedReservationTimeException("이미 존재하는 예약 시간입니다. 다른 시간대를 입력해주세요.");
        }
    }

    public List<ReservationTimeAvailableResponse> findAvailableReservationTime(String date, Long themeId) {
        Theme theme = themeRepository.findThemeById(themeId).orElseThrow(() ->
                new ThemeNotFoundException(ErrorCode.THEME_NOT_FOUND,"해당 테마가 존재하지 않습니다."));

        List<ReservationTime> times = reservationTimeRepository.findTimes();
        return times.stream()
                .filter(time -> !reservationRepository.existsByDateAndThemeAndTime(theme, time, date))
                .map(ReservationTimeAvailableResponse::fromReservationTime)
                .collect(Collectors.toList());
    }
}
