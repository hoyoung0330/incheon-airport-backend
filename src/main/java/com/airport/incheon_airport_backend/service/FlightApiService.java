package com.airport.incheon_airport_backend.service;

import com.airport.incheon_airport_backend.dto.FlightDto;
import com.airport.incheon_airport_backend.dto.FlightListResponseDto;
import com.airport.incheon_airport_backend.dto.FlightResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class FlightApiService {

    @Value("${airport.api.key}")
    private String apiKey;

    @Value("${airport.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 도착편 조회
    public FlightListResponseDto getArrivals(String airportCode) {
        String json = callApi("/StatusOfPassengerFlightsDSOdp/getPassengerArrivalsDSOdp", airportCode);
        return parseResponse(json);
    }

    // 출발편 조회
    public FlightListResponseDto getDepartures(String airportCode) {
        String json = callApi("/StatusOfPassengerFlightsDSOdp/getPassengerDeparturesDSOdp", airportCode);
        return parseResponse(json);
    }

    // API 호출 공통 메서드
    private String callApi(String endpoint, String airportCode) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(baseUrl + endpoint)
                .queryParam("serviceKey", apiKey)
                .queryParam("type", "json");

        if (airportCode != null && !airportCode.isEmpty()) {
            builder.queryParam("airport_code", airportCode);
        }

        URI uri = builder.build(true).toUri();
        log.debug("API 호출: {}", uri);

        try {
            return restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            log.error("API 호출 실패: {}", e.getMessage());
            throw new RuntimeException("API 호출 실패", e);
        }
    }

    // JSON 파싱 공통 메서드
    private FlightListResponseDto parseResponse(String json) {
        try {
            FlightResponseDto response =
                objectMapper.readValue(json, FlightResponseDto.class);

            if (response.getResponse() == null ||
                response.getResponse().getBody() == null) {
                return FlightListResponseDto.builder()
                        .totalCount(0)
                        .flights(Collections.emptyList())
                        .build();
            }

            FlightResponseDto.Body body = response.getResponse().getBody();
            List<FlightDto> flights =
                body.getItems() != null ? body.getItems() : Collections.emptyList();

            return FlightListResponseDto.builder()
                    .totalCount(body.getTotalCount())
                    .flights(flights)
                    .build();

        } catch (Exception e) {
            log.error("JSON 파싱 실패: {}", e.getMessage());
            throw new RuntimeException("데이터 파싱 실패", e);
        }
    }
}