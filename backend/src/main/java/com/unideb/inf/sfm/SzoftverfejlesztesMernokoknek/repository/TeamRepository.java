package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
