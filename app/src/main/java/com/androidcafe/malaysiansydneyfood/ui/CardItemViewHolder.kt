package com.androidcafe.malaysiansydneyfood.ui

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.androidcafe.malaysiansydneyfood.databinding.CardItemBinding
import com.androidcafe.malaysiansydneyfood.viewmodel.CardData
import android.widget.Toast

import android.R

import android.content.ActivityNotFoundException
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CardItemViewHolder(private val binding: CardItemBinding, private val viewModel: SharedFragmentViewModel)
    : RecyclerView.ViewHolder(binding.root) {

    private lateinit var _cardData: CardData

    fun bindData(cardData: CardData) {
        _cardData = cardData
        binding.cardData = cardData
        binding.viewHolder = this
        binding.executePendingBindings()
    }

    fun onCardClick(cardData: CardData) {

        val uri = Uri.parse(cardData.mapUrl);
        val intent = Intent(Intent.ACTION_VIEW, uri)

        // Note: intent.resolveActivity no longer works in Android 11 due to package visiblity
        try {
            intent.setPackage("com.google.android.apps.maps")
            itemView.context.startActivity(intent)

        } catch (e: ActivityNotFoundException) {

            try {
                intent.setPackage(null)
                itemView.context.startActivity(intent)

            } catch (e: ActivityNotFoundException) {
                val snack = Snackbar.make(
                    itemView,
                    "Please install Google Map or Web Browser!",Snackbar.LENGTH_LONG)
                snack.show()
            }
        }
    }

    fun onFavoriteClick(cardData: CardData) {

        GlobalScope.launch {
            viewModel.update(cardData)
            binding.cardData = cardData
        }
    }
}