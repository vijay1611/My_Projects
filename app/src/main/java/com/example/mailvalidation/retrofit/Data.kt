package com.example.mailvalidation.retrofit

data class Data(
    val `data`: List<DataX>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
)