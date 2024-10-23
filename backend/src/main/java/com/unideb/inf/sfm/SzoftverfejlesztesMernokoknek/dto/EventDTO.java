package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private String eventName;
    private int eventYear;
}
