package com.androidcafe.malaysiansydneyfood.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidcafe.malaysiansydneyfood.repository.FoodRepository

class SharedFragmentViewModelFactory(private val app: Application, private val repository: FoodRepository) :
    ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedFragmentViewModel::class.java)) {

            return SharedFragmentViewModel(app, repository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}