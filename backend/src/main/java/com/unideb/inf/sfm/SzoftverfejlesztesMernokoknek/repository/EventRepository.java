package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
