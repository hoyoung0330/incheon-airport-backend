package com.airport.incheon_airport_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDto {
    private String airline;          // 항공사
    private String flightId;         // 편명
    private String scheduleDateTime; // 예정시간 (YYYYMMDDHHMM)
    private String estimatedDateTime;// 변경시간
    private String airport;          // 상대공항명
    private String airportCode;      // 상대공항코드 (IATA)
    private String remark;           // 운항상태
    private String terminalid;       // 터미널 구분
    private String gatenumber;       // 게이트번호
    private String carousel;         // 수하물수취대 (도착편)
    private String chkinrange;       // 체크인카운터 (출발편)
    private String codeshare;        // 코드쉐어
    private String masterflightid;   // 마스터편명
}