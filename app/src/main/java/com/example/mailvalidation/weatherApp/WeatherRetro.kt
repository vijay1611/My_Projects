package com.example.mailvalidation.weatherApp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRetro {

    const val base_url = "https://goweather.herokuapp.com/"

    private var retrofit: Retrofit? = null
    fun getData(): Retrofit? {
         if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun  getRetrofitApi():WeatherDao?{
      return  getData()?.create(WeatherDao::class.java)
    }

}



