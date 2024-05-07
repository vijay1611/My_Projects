package com.example.mailvalidation

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CartoonInterface {

    @GET("cartoons2D")
    fun getData(): Call<List<CartoonItem>>
}