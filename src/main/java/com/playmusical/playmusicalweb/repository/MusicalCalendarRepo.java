package com.playmusical.playmusicalweb.repository;

import com.playmusical.playmusicalweb.entity.MusicalCalendar;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicalCalendarRepo extends JpaRepository<MusicalCalendar, Long> {

    MusicalCalendar findByMusical_MusicalNoAndPerformanceDateGreaterThanEqualAndPerformanceDateLessThan(
        long musicalNo, LocalDateTime performanceDateStart, LocalDateTime performanceDateEnd);

    MusicalCalendar findByPerformanceNo(long performanceNo);

}
