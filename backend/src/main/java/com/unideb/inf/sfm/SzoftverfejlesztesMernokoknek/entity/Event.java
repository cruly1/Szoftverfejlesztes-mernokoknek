package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name", nullable = false, length = 50)
    private String eventName;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @ManyToOne
    private Team team;
}
