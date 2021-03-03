package com.vedant.notes.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes(
    val title:String,
    val content:String,
    val color:Long,
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L
){

}