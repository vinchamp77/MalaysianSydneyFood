package com.androidcafe.malaysiansydneyfood.repository

import androidx.lifecycle.asLiveData
import com.androidcafe.malaysiansydneyfood.local.FoodEntity
import com.androidcafe.malaysiansydneyfood.local.IFoodDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository (private val dao: IFoodDao) {

    val allFoodEntityList = dao.getAll().asLiveData()
    val favFoodEntityList = dao.getByFavorite().asLiveData()

    suspend fun getByTitle(title: String): List<FoodEntity> {
        return withContext(Dispatchers.IO) {
            dao.getByTitle("%$title%")
        }
    }

    suspend fun getByTitleFavoriteFood(title: String): List<FoodEntity> {
        return withContext(Dispatchers.IO) {
            dao.getByTitleFavoriteFood("%$title%")
        }
    }

    suspend fun update(foodEntity: FoodEntity) {
        return withContext(Dispatchers.IO) {
            dao.update(foodEntity)
        }
    }
}