package com.example.mailvalidation.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mailvalidation.CartoonItem

@Database(entities = [CartoonItem::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class CartoonDatabase:RoomDatabase() {
    abstract fun cartoonDao(): CartoonDao

    companion object {
    private val LOCK = Any()
    val DB_NAME = "interlocutor.db"

    @Volatile
    public var dbInstance: CartoonDatabase? = null

    fun getInstance(application: Application):CartoonDatabase{
        synchronized(LOCK) {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(application, CartoonDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
        }
        return dbInstance!!
    }
}


}