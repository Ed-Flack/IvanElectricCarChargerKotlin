package com.oocode

import com.opencsv.CSVReader
import okhttp3.OkHttpClient
import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Request
import java.io.File
import java.io.StringReader
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME

fun main() {
    val weatherData = getWeatherDataFromAPI()
    println(getTopThreeWind(weatherData, mutableListOf(WeatherFields.EMBEDDED_WIND_FORECAST, WeatherFields.EMBEDDED_SOLAR_FORECAST)))
}

fun getTopThreeWind(weatherData: String, fieldsToSortBy: List<WeatherFields>): String {
    val weather = getWeatherRows(weatherData)
    return "Best times to plug in:\n" + topThreeRowsFormatted(weather, fieldsToSortBy).joinToString("\n")
}

private fun topThreeRowsFormatted(lines: List<Weather>, fieldsToSortBy: List<WeatherFields>): List<String> {
    if (fieldsToSortBy.isNotEmpty()) {
       return lines.sortedByDescending {it.getFieldAccumulated(fieldsToSortBy)}
           .take(3)
            .map { row -> createZonedDateTime(row.dateGmt, row.timeGmt) }
            .sorted()
            .map { it.format(RFC_1123_DATE_TIME)}
    }
    return lines.take(3)
        .map { row -> createZonedDateTime(row.dateGmt, row.timeGmt) }
        .sorted()
        .map { it.format(RFC_1123_DATE_TIME) }
}

private fun createZonedDateTime(date: String, time: String): ZonedDateTime = ZonedDateTime.of(
    LocalDateTime.parse(date)
        .withHour(time.split(":")[0].toInt())
        .withMinute(time.split(":")[1].toInt()),
    ZoneId.of(timezone)
)

private fun getWeatherRows(weatherData: String): List<Weather> {
    val lines = CSVReader(StringReader(weatherData)).readAll().toList()
    return Weather.ofList(lines.drop(1))
}

private fun getWeatherDataFromAPI(): String {
    val httpClient = OkHttp(OkHttpClient.Builder().followRedirects(true).build())
    val contents = httpClient(Request(Method.GET, nationalGridEsoDataUrl)).bodyString()
    return contents
}
