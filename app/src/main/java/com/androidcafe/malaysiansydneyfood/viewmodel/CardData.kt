package com.androidcafe.malaysiansydneyfood.viewmodel

import android.graphics.drawable.Drawable

data class CardData (
    val id : Int,
    val title : String,
    val rating : Float,
    val description : String,
    val imageUrl : String,
    val mapUrl : String,
    var favorite : Boolean?,
)
