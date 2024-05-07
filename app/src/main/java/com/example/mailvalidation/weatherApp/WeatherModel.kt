package com.example.mailvalidation.weatherApp

data class WeatherModel(
    val description: String,
    val forecast: List<Forecast>,
    val temperature: String,
    val wind: String
)