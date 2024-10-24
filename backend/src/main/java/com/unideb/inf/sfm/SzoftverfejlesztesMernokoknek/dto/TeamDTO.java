package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private String teamName;
    private List<String> players;
}
