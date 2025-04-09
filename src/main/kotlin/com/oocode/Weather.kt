package com.oocode
class Weather(
    val dateGmt: String,
    val timeGmt: String,
    val settlementDate: String,
    val settlementPeriod: String,
    val embeddedWindForecast: String,
    val embeddedWindCapacity: String,
    val embeddedSolarForecast: String,
    val embeddedSolarCapacity: String
) {
    companion object {
        fun of(row: Array<String>): Weather {
            return Weather(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7])
        }

        fun ofList(rows: List<Array<String>>): List<Weather> {
            val list = mutableListOf<Weather>()
            for (row in rows) {
                list.add(of(row))
            }
            return list
        }
    }
}