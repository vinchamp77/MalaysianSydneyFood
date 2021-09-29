package com.androidcafe.malaysiansydneyfood.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.androidcafe.malaysiansydneyfood.databinding.CardItemBinding
import com.androidcafe.malaysiansydneyfood.viewmodel.CardData
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModel

class CardRecycleViewAdapter(private val viewModel: SharedFragmentViewModel)
    : ListAdapter<CardData, CardItemViewHolder> (CardDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {

        //Log.d("VTSEN", "onCreateViewHolder()")
        val cardItemBinding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return CardItemViewHolder(cardItemBinding, viewModel)
    }
    override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {

        //Log.d("VTSEN", "onBindViewHolder()")
        holder.bindData(getItem(position))
    }
}