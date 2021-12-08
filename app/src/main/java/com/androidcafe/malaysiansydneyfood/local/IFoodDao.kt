package com.androidcafe.malaysiansydneyfood.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IFoodDao {
    @Query("SELECT * FROM food ORDER BY rating DESC")
    fun getAll(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM food WHERE title LIKE :query OR suburb LIKE :query ORDER BY rating DESC")
    fun getBySearchQueryAllFood(query: String): List<FoodEntity>

    @Query("SELECT * FROM food WHERE (title LIKE :query OR suburb LIKE :query) AND favorite = 1 ORDER BY rating DESC")
    fun getBySearchQueryFavoriteFood(query: String): List<FoodEntity>

    @Query("SELECT * FROM food WHERE favorite = 1 ORDER BY rating DESC")
    fun getByFavorite(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM food WHERE id = :id")
    fun getById(id: Int): FoodEntity?

    @Update
    fun update(vararg foodEntities:FoodEntity)
}