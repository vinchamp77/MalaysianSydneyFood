package com.androidcafe.malaysiansydneyfood.repository

import androidx.lifecycle.asLiveData
import com.androidcafe.malaysiansydneyfood.local.IFoodDao
import com.androidcafe.malaysiansydneyfood.local.FoodEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FoodRepository (private val dao: IFoodDao) {

    val foodEntityList = dao.getAll().asLiveData()

    suspend fun getByTitle(title: String): Flow<List<FoodEntity>> {
        return withContext(Dispatchers.IO) {
            //Log.d("VTSEN", "I'm working in thread ${Thread.currentThread().name}")
            //delay(3000)
            //return@withContext dao.getAll()
            dao.getByTitle(title)
        }
    }

    suspend fun getByFavorite(): Flow<List<FoodEntity>> {
        return withContext(Dispatchers.IO) {
            //Log.d("VTSEN", "I'm working in thread ${Thread.currentThread().name}")
            //delay(3000)
            //return@withContext dao.getAll()
            dao.getByFavorite()
        }
    }

    suspend fun update(foodEntity: FoodEntity) {
        return withContext(Dispatchers.IO) {
            //Log.d("VTSEN", "I'm working in thread ${Thread.currentThread().name}")
            //delay(3000)
            //return@withContext dao.getAll()
            dao.update(foodEntity)
        }
    }

}