package com.androidcafe.malaysiansydneyfood.ui

import androidx.recyclerview.widget.DiffUtil
import com.androidcafe.malaysiansydneyfood.viewmodel.CardData

object CardDiffCallback : DiffUtil.ItemCallback<CardData>() {
    override fun areItemsTheSame(oldItem: CardData, newItem: CardData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CardData, newItem: CardData): Boolean {
        return (oldItem == newItem)
    }
}