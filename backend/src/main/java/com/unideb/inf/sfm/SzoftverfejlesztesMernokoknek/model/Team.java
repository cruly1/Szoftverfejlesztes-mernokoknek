package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "team_gen")
    @TableGenerator(
            name = "team_gen",
            table = "id_gen_table",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "team_id",
            initialValue = 3000,
            allocationSize = 1
    )
    private Long id;

    @Column(name = "team_name", nullable = false, unique = true, length = 20)
    private String teamName;

    @OneToMany(mappedBy = "team")
    private List<Player> playersInTeam = new ArrayList<>();

    @ManyToMany(mappedBy = "teams")
    private Set<Event> events = new HashSet<>();
}
