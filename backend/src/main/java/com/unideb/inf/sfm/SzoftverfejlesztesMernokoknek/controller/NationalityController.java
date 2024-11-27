package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.controller;

import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service.NationalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/nationalities/")
@RequiredArgsConstructor
public class NationalityController {

    private final NationalityService nationalityService;

    @GetMapping("getAllDemonyms")
    public ResponseEntity<List<String>> getAllDemonyms() {
        List<String> demonyms = nationalityService.getAllCountries();
        return ResponseEntity.ok(demonyms);
    }
}
