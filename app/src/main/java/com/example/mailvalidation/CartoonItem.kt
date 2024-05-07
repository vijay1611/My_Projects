package com.example.mailvalidation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartoonItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val creator: List<String>,
    val episodes: Int,
    val genre: List<String>,
    val image: String,
    val rating: String,
    val runtime_in_minutes: Int,
    val title: String,
    val year: Int
)