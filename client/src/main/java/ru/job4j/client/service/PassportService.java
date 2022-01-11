package ru.job4j.client.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.job4j.client.domain.Passport;

import java.util.List;
import java.util.UUID;

/**
 * Class PassportService.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 21.12.2021
 */
@Service
@EnableScheduling
public class PassportService {

    private final KafkaTemplate<String, List<Passport>> kafkaTemplate;
    private final RestTemplate rest;

    @Value("${api-server}")
    private String api;

    public PassportService(KafkaTemplate<String, List<Passport>> kafkaTemplate, RestTemplate rest) {
        this.kafkaTemplate = kafkaTemplate;
        this.rest = rest;
    }

    public List<Passport> findAll() {
        return rest.exchange(
                String.format("%s/find", api),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
    }

    public Passport create(Passport passport) {
        return rest.postForObject(api, passport, Passport.class);
    }

    public boolean update(int id, Passport passport) {
        return rest.exchange(
                String.format("%s?id=%s", api, id),
                HttpMethod.PUT,
                new HttpEntity<>(passport),
                Void.class
        ).getStatusCode() == HttpStatus.OK;
    }

    public boolean delete(int id) {
        return rest.exchange(
                String.format("%s?id=%s", api, id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        ).getStatusCode() == HttpStatus.OK;
    }

    public Passport findBySeries(String series) {
        return rest.getForEntity(
                String.format("%s/find/%s", api, series),
                Passport.class
        ).getBody();
    }

    public List<Passport> getExpiredPassport() {
        return rest.exchange(
                String.format("%s/unavailable", api),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
    }

    public List<Passport> getReplaceablePassport() {
        return rest.exchange(
                String.format("%s/find-replaceable", api),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
    }

    @Scheduled(fixedDelay = 10000)
    public void checkPassportByDate() {
        List<Passport> passports = getExpiredPassport();
        if (!passports.isEmpty()) {
            kafkaTemplate.send("mail", UUID.randomUUID().toString(), passports);
        }
    }
}
