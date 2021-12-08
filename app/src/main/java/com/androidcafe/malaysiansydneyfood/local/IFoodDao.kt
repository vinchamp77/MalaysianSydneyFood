package com.androidcafe.malaysiansydneyfood.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface IFoodDao {
    @Query("SELECT * FROM food ORDER BY rating DESC")
    fun getAll(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM food WHERE title LIKE :titleToQuery ORDER BY rating DESC")
    fun getByTitle(titleToQuery: String): List<FoodEntity>

    @Query("SELECT * FROM food WHERE title LIKE :titleToQuery AND favorite = 1 ORDER BY rating DESC")
    fun getByTitleFavoriteFood(titleToQuery: String): List<FoodEntity>

    @Query("SELECT * FROM food WHERE favorite = 1 ORDER BY rating DESC")
    fun getByFavorite(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM food WHERE id = :id")
    fun getById(id: Int): FoodEntity?

    @Update
    fun update(vararg foodEntities:FoodEntity)
}