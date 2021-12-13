package com.androidcafe.malaysiansydneyfood.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.androidcafe.malaysiansydneyfood.R
import com.androidcafe.malaysiansydneyfood.databinding.CardItemBinding
import com.androidcafe.malaysiansydneyfood.viewmodel.CardData
import com.androidcafe.malaysiansydneyfood.viewmodel.SharedFragmentViewModel
import com.google.android.material.snackbar.Snackbar

class CardItemViewHolder(
    private val binding: CardItemBinding,
    private val viewModel: SharedFragmentViewModel,
    private val navController: NavController?
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var _cardData: CardData

    fun bindData(cardData: CardData) {
        _cardData = cardData
        binding.cardData = cardData
        binding.viewHolder = this
        binding.executePendingBindings()
    }

    fun onCardClick() {

        val uri = Uri.parse(_cardData.mapUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        // Note: intent.resolveActivity no longer works in Android 11 due to package visibility
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

    fun onFavoriteClick() {

        if (_cardData.favorite == null) {
            _cardData.favorite = true
        } else {
            _cardData.favorite = !_cardData.favorite!!
        }
        viewModel.update(_cardData)
        binding.cardData = _cardData
    }

    fun onSuburbClick() {
        navController?.run {
            viewModel.setSearchInfo(_cardData.suburb, false)
            this.navigate(R.id.action_main_fragment_to_search_result_fragment)
        }
    }
}