package com.playmusical.playmusicalweb.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Musical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicalNo;

    private String musicalTitle;

    private LocalDate startDate;

    private LocalDate endDate;

    private String location;

    private LocalTime startTime;

    private LocalTime endTime;

    private String posterImg;

    private String bannerImg;

    @OneToMany(mappedBy = "musical", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<MusicalCalendar> musicalCalendarList = new ArrayList<>();

}
