package com.androidcafe.malaysiansydneyfood.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "food")
data class FoodEntity (
    @PrimaryKey val id: Int,
    val title : String,
    val rating : Float,
    val description : String,
    val imageUrl : String,
    val mapUrl : String,
    val favorite : Boolean?
)