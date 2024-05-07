package com.example.mailvalidation.weatherApp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherDao{
    @GET("weather/{city}")
     fun getWeather(
        @Path("city") city: String,
    ):Call<WeatherModel>

}