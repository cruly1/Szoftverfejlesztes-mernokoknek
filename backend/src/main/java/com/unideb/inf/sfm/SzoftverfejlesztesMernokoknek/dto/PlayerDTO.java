package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Event;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.EGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String nickName;
    private LocalDate birthDate;
    private String teamName;
    private Event event;
    private EGender gender;
    private String nationality;
}
