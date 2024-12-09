package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NationalityRepository extends JpaRepository<Nationality, Long> {
    Optional<Nationality> findByCountryName(String countryName);
}
