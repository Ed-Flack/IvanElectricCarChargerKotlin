package com.oocode

enum class WeatherFields {
    DATE_GMT,
    TIME_GMT,
    SETTLEMENT_DATE,
    SETTLEMENT_PERIOD,
    EMBEDDED_WIND_FORECAST,
    EMBEDDED_WIND_CAPACITY,
    EMBEDDED_SOLAR_FORECAST,
    EMBEDDED_SOLAR_CAPACITY
}

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
    fun getField(weatherField: WeatherFields): String {
        when (weatherField) {
            WeatherFields.DATE_GMT -> return this.dateGmt
            WeatherFields.TIME_GMT -> return this.timeGmt
            WeatherFields.SETTLEMENT_DATE -> return this.settlementDate
            WeatherFields.SETTLEMENT_PERIOD -> return this.settlementPeriod
            WeatherFields.EMBEDDED_WIND_FORECAST -> return this.embeddedWindForecast
            WeatherFields.EMBEDDED_WIND_CAPACITY -> return this.embeddedWindCapacity
            WeatherFields.EMBEDDED_SOLAR_FORECAST -> return this.embeddedSolarForecast
            WeatherFields.EMBEDDED_SOLAR_CAPACITY -> return this.embeddedSolarCapacity
        }
    }

    fun getFieldAccumulated(weatherFields: List<WeatherFields>): Int {
        var total = 0
        for (weatherField in weatherFields) {
            total += this.getField(weatherField).toInt()
        }
        return total
    }

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