package com.aztgg.api.recruitmentnoticestatistic.application.dto;

import java.time.LocalDate;

public record GetDailyStatisticCacheDto(LocalDate date, int count) {

}