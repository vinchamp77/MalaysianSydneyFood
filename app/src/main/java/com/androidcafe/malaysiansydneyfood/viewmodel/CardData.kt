package com.androidcafe.malaysiansydneyfood.viewmodel

import com.androidcafe.malaysiansydneyfood.model.FoodEntity

data class CardData (
    val id : Int,
    val title : String,
    val rating : Float,
    val description : String,
    val imageUrl : String,
    val mapUrl : String,
    var favorite : Boolean?,
)

fun CardData.asFoodEntity(toggleFavorite: Boolean = false): FoodEntity {

    var modifiedFavorite = favorite

    if(toggleFavorite) {
        modifiedFavorite = favorite != true
    }

    return FoodEntity(
        id = id,
        title = title,
        rating = rating,
        description = description,
        imageUrl = imageUrl,
        mapUrl = mapUrl,
        favorite = modifiedFavorite,
    )
}
