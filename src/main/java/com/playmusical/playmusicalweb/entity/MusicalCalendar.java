package com.playmusical.playmusicalweb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MusicalCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long performanceNo;

    private LocalDateTime performanceDate;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "musical_no")
    private Musical musical;

    private LocalDateTime cancelDate;


    @JsonBackReference
    @OneToMany(mappedBy = "musicalCalendar", fetch = FetchType.EAGER)
    private List<Reservation> reservationList = new ArrayList<>();

}
