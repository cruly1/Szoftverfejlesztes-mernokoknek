package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String teamName;
}
