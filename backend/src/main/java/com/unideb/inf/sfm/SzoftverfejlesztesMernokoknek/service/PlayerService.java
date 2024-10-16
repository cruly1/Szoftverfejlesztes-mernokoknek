package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.entity.Player;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository userRepository;

    public Player addUser(Player player) {
        return userRepository.save(player);
    }

    public String removeUserById(Long userId) {
        userRepository.deleteById(userId);
        return "Player deleted";
    }

    public List<Player> getAll() {
        return userRepository.findAll();
    }
}
