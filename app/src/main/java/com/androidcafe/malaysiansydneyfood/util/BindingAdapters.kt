package com.androidcafe.malaysiansydneyfood.util

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androidcafe.malaysiansydneyfood.R
import com.androidcafe.malaysiansydneyfood.ui.CardRecycleViewAdapter
import com.androidcafe.malaysiansydneyfood.viewmodel.CardData
import com.bumptech.glide.Glide

@BindingAdapter("cardDataList")
fun bindRecyclerView(recyclerView: RecyclerView, cardDataList: List<CardData>?) {
    val adapter = recyclerView.adapter as CardRecycleViewAdapter
    adapter.submitList(cardDataList)
}

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, url: String) {

    val debug = false
    if (debug) {
        val debugUrl =
            "https://lh3.googleusercontent.com/p/AF1QipM_YYPB3qJF88PYe8tVaUx91WzTv-AYBBc3o7L8"
        Glide.with(imageView.context)
            .load(debugUrl)
            .error(R.drawable.error_loading)
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .load(url)
            .error(R.drawable.error_loading)
            .into(imageView)
    }
}

@BindingAdapter("srcCompat")
fun bindSrcCompat(imageView: ImageView, favorite: Boolean) {
    val imageResourceId: Int =
        if (favorite) R.drawable.ic_favorite_selected
        else R.drawable.ic_favorite_unselected

        imageView.setImageResource(imageResourceId)
}
