package com.example.mailvalidation.retrofit

import com.example.mailvalidation.weatherApp.WeatherModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface DataDao {
    @GET("users")
    fun getUsers(): Call<Data>
}