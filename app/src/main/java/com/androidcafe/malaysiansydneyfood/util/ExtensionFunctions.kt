package com.androidcafe.malaysiansydneyfood.util

import android.service.autofill.Validators.not
import com.androidcafe.malaysiansydneyfood.model.FoodEntity
import com.androidcafe.malaysiansydneyfood.viewmodel.CardData

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