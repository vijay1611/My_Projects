package com.example.mailvalidation.Retrofit2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper{
    val bae_url = "https://quotable.io/"


    fun getInstance():Retrofit{
        return  Retrofit.Builder().baseUrl(bae_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}