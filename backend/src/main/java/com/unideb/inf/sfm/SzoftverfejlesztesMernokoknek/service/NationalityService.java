package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Nationality;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.NationalityRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NationalityService {

    private static Long nationalityId = 6000L;

    private final NationalityRepository nationalityRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void loadNationalities() {
        String api = "https://restcountries.com/v3.1/all";
        Country[] countries = restTemplate.getForObject(api, Country[].class);

        if (countries != null) {
            for (Country country : countries) {
                try {
                    String countryName = country.getName().getCommon();
                    String demonym = country.getDemonyms().getEng().getM();
                    String cca2 = country.getCca2();

                    if (countryName != null && demonym != null && cca2 != null) {
                        Nationality nationalityEntity = new Nationality(nationalityId, countryName, demonym, cca2);
                        nationalityRepository.save(nationalityEntity);
                        nationalityId++;
                    }
                } catch (BeanCreationException | NullPointerException e) {
                    System.out.println("Invalid countryname, demonym or countrycode!");
                }
            }
        }
    }

    public List<String> getAllDemonyms() {
        return nationalityRepository.findAll()
                .stream()
                .map(Nationality::getDemonym)
                .sorted()
                .collect(Collectors.toList());
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
class Country {
    private Name name;
    private Demonyms demonyms;
    private String cca2;

    @Getter
    @Setter
    static class Name {
        private String common;
    }

    @Getter
    @Setter
    static class Demonyms {
        private Demonym eng;

        @Getter
        @Setter
        static class Demonym {
            private String m;
        }
    }
}
