package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "event_gen")
    @TableGenerator(
            name = "event_gen",
            table = "id_gen_table",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "event_id",
            initialValue = 4000,
            allocationSize = 1
    )
    private Long id;

    @Column(name = "event_name", nullable = false, length = 50)
    private String eventName;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @ManyToMany
    @JoinTable(
            name = "event_player",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    @JsonIgnore
    private Set<Player> players = new HashSet<>();

    @ManyToMany(mappedBy = "events")
    private Set<Team> teams = new HashSet<>();
}
