package com.example.roughnote

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var title:String,
    var desc: String,
    var image:String,
    var date: Date
)
