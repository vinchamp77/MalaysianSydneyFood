package com.androidcafe.malaysiansydneyfood.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidcafe.malaysiansydneyfood.viewmodel.CardData

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

fun List<FoodEntity>.asCardDataList(): List<CardData> {
    return map {
        CardData(
            id = it.id,
            title = it.title,
            rating = it.rating,
            description = it.description,
            imageUrl = it.imageUrl,
            mapUrl = it.mapUrl,
            favorite = it.favorite,
        )
    }
}
