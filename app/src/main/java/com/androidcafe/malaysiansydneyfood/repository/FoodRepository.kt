package com.androidcafe.malaysiansydneyfood.repository

import androidx.lifecycle.asLiveData
import com.androidcafe.malaysiansydneyfood.local.FoodEntity
import com.androidcafe.malaysiansydneyfood.local.IFoodDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository (private val dao: IFoodDao) {

    val allFoodEntityList = dao.getAll().asLiveData()
    val favFoodEntityList = dao.getByFavorite().asLiveData()

    suspend fun getBySearchQueryAllFood(query: String): List<FoodEntity> {
        return withContext(Dispatchers.IO) {
            dao.getBySearchQueryAllFood("%$query%")
        }
    }

    suspend fun getBySearchQueryFavoriteFood(query: String): List<FoodEntity> {
        return withContext(Dispatchers.IO) {
            dao.getBySearchQueryFavoriteFood("%$query%")
        }
    }

    suspend fun getById(id: Int): FoodEntity? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }

    suspend fun update(foodEntity: FoodEntity) {
        return withContext(Dispatchers.IO) {
            dao.update(foodEntity)
        }
    }
}