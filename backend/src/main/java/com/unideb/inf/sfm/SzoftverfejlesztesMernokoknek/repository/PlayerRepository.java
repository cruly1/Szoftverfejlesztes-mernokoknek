package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByNickName(String nickName);
}
