package com.example.mailvalidation.retrofit

import com.example.mailvalidation.weatherApp.WeatherDao
import com.example.mailvalidation.weatherApp.WeatherRetro
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataSource {
    const val base_url = "https://reqres.in/api/"

    private var retrofit: Retrofit? = null
    fun getData(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun  getRetrofitApi(): DataDao?{
        return  getData()?.create(DataDao::class.java)
    }
}