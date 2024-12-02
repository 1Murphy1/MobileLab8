package com.example.retrofitforecaster

data class ForecastResponse(
    val list: List<WeatherForecast>
)

data class WeatherForecast(
    val dt_txt: String,
    val main: MainData,
    val weather: List<WeatherData>
)

data class MainData(
    val temp: Float
)

data class WeatherData(
    val description: String,
    val icon: String
)
