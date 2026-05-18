package com.airport.incheon_airport_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FlightListResponseDto {
    private int totalCount;
    private List<FlightDto> flights;
}