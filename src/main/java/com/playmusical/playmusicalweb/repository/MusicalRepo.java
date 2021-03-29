package com.playmusical.playmusicalweb.repository;

import com.playmusical.playmusicalweb.entity.Musical;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicalRepo extends JpaRepository<Musical, Long> {

    Page<Musical> findByMusicalTitleContainingAndEndDateAfter(String musicalTitle,
        LocalDate endDate, Pageable pageable);

    List<Musical> findByMusicalTitleContainingAndEndDateAfter(String musicalTitle,
        LocalDate endDate);

    Page<Musical> findByEndDateAfterOrderByEndDate(LocalDate endDate, Pageable pageable);
}
