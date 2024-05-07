package com.example.mailvalidation.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.mailvalidation.CartoonItem

@Dao
interface CartoonDao {
    @Query("Select * from CartoonItem")
    suspend fun getAllCartoonUsers(): List<CartoonItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(cartoonItem: List<CartoonItem>)
}

