package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Nationality;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.NationalityRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
                String countryName = country.getName().getCommon();
                String translation = country.getTranslations().getHun().getOfficial();

                if (countryName != null && translation != null) {
                    Nationality nationalityEntity = new Nationality(nationalityId, countryName, translation);
                    nationalityRepository.save(nationalityEntity);
                    nationalityId++;
                }
            }
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
class Country {
    private Name name;
    private Translations translations;

    @Getter
    @Setter
    static class Name {
        private String common;

    }

    @Getter
    @Setter
    static class Translations {
        private Translation hun;

        @Getter
        @Setter
        static class Translation {
            private String official;

        }
    }
}
