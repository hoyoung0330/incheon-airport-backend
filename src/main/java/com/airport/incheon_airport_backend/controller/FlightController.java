package com.airport.incheon_airport_backend.controller;

import com.airport.incheon_airport_backend.common.ApiResponse;
import com.airport.incheon_airport_backend.service.FlightApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightApiService flightApiService;

    // 여객기 주간 운항 도착편 조회
    @GetMapping("/arrivals")
    public ApiResponse<String> getArrivals(
            @RequestParam(required = false) String airportCode) {
        return ApiResponse.ok(flightApiService.getArrivals(airportCode));
    }

    // 여객기 주간 운항 출발편 조회
    @GetMapping("/departures")
    public ApiResponse<String> getDepartures(
            @RequestParam(required = false) String airportCode) {
        return ApiResponse.ok(flightApiService.getDepartures(airportCode));
    }
}