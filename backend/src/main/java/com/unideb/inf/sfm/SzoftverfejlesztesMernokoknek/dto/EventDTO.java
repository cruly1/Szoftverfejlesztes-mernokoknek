package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String eventName;
    private LocalDate eventDate;
//    private Set<PlayerDTO> players = new HashSet<>();
//    private Set<TeamDTO> teams = new HashSet<>();
}
