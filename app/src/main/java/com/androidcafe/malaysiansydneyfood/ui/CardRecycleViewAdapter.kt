package com.androidcafe.malaysiansydneyfood.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import com.androidcafe.malaysiansydneyfood.databinding.CardItemBinding
import com.androidcafe.malaysiansydneyfood.viewmodel.CardData
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModel

class CardRecycleViewAdapter(
    private val viewModel: SharedFragmentViewModel,
    private val navController: NavController?)
    : ListAdapter<CardData, CardItemViewHolder> (CardDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {

        val cardItemBinding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return CardItemViewHolder(cardItemBinding, viewModel, navController)
    }
    override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {

        holder.bindData(getItem(position))
    }
}