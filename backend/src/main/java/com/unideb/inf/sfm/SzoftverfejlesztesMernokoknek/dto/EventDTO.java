package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private String eventName;
    private LocalDate eventEndDate;
    private LocalDate eventStartDate;
}
