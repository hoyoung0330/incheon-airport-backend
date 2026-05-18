package com.airport.incheon_airport_backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class FlightApiService {

    @Value("${airport.api.key}")
    private String apiKey;

    @Value("${airport.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // 여객기 주간 운항 정보(도착) 조회
    public String getArrivals(String airportCode) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/StatusOfPassengerFlightsDSOdp/getPassengerArrivalsDSOdp")
                .queryParam("serviceKey", apiKey)
                .queryParam("type", "json");

        if (airportCode != null && !airportCode.isEmpty()) {
            builder.queryParam("airport_code", airportCode);
        }

        URI uri = builder.build(true).toUri();
        log.debug("도착편 API 호출: {}", uri);

        try {
            return restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            log.error("도착편 API 호출 실패: {}", e.getMessage());
            throw new RuntimeException("도착편 API 호출 실패", e);
        }
    }

    // 여객기 주간 운항 정보(출발) 조회
    public String getDepartures(String airportCode) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(baseUrl + "/StatusOfPassengerFlightsDSOdp/getPassengerDeparturesDSOdp")
                .queryParam("serviceKey", apiKey)
                .queryParam("type", "json");

        if (airportCode != null && !airportCode.isEmpty()) {
            builder.queryParam("airport_code", airportCode);
        }

        URI uri = builder.build(true).toUri();
        log.debug("출발편 API 호출: {}", uri);

        try {
            return restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            log.error("출발편 API 호출 실패: {}", e.getMessage());
            throw new RuntimeException("출발편 API 호출 실패", e);
        }
    }
}