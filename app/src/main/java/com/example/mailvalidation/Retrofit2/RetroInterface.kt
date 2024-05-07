package com.example.mailvalidation.Retrofit2

import retrofit2.Response
import retrofit2.http.GET

interface RetroInterface {

    @GET("/quotes")
    suspend fun getQuotes(): Response<Quote>
}