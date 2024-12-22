package com.evawova.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// yyyyMMdd로 변환
fun LocalDate.toYyyyMMdd(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    return this.format(formatter)
}

fun LocalDateTime.toYyyyMMdd(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    return this.toLocalDate().format(formatter)
}

// HHmmss로 변환
fun LocalDateTime.toHhmmss(): String {
    val formatter = DateTimeFormatter.ofPattern("HHmmss")
    return this.format(formatter)
}

// yyyyMMdd를 LocalDate로 변환
fun String.toLocalDateFromYyyyMMdd(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    return LocalDate.parse(this, formatter)
}

// HHmmss를 LocalTime으로 변환
fun String.toLocalTimeFromHhmmss(): LocalTime {
    val formatter = DateTimeFormatter.ofPattern("HHmmss")
    return LocalTime.parse(this, formatter)
}

// yyyyMMdd와 HHmmss를 LocalDateTime으로 변환
fun String.toLocalDateTimeFromYyyyMMddAndHhmmss(hhmmss: String): LocalDateTime {
    val date = this.toLocalDateFromYyyyMMdd()
    val time = hhmmss.toLocalTimeFromHhmmss()
    return LocalDateTime.of(date, time)
}

fun Long.toLocalDateTime(zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zoneId)
