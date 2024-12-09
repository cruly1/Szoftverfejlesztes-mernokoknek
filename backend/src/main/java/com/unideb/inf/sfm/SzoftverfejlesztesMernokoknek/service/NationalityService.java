package com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.model.Nationality;
import com.unideb.inf.sfm.SzoftverfejlesztesMernokoknek.repository.NationalityRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NationalityService {

    private static Long nationalityId = 6000L;

    @Value("${nationality.accessKey}")
    private String accessKey;

    private final NationalityRepository nationalityRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(NationalityService.class);

    @PostConstruct
    public void loadNationalities() {
        String api = "https://api.countrylayer.com/v2/all?access_key=" + accessKey;

        try {
            Country[] countries = restTemplate.getForObject(api, Country[].class);

            if (countries != null) {
                for (Country country : countries) {
                    try {
                        if (country.getName() != null && country.getAlpha2Code() != null) {

                            String countryName = country.getName();
                            String countryCode = country.getAlpha2Code();

                            Nationality nationalityEntity = new Nationality(nationalityId, countryName, countryCode);
                            nationalityRepository.save(nationalityEntity);
                            nationalityId++;
                        }
                    } catch (BeanCreationException | NullPointerException e) {
                        logger.warn(e.getMessage());
                    }
                }
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("HTTP error: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (ResourceAccessException e) {
            logger.error("Network error: " + e.getMessage());
        } catch (RestClientException e) {
            logger.error("General RestClientException: " + e.getMessage());
        }
    }

    public List<String> getAllCountries() {
        return nationalityRepository.findAll()
                .stream()
                .map(Nationality::getCountryName)
                .sorted()
                .collect(Collectors.toList());
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
class Country {
    private String name;
    private String alpha2Code;
}
