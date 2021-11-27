package com.androidcafe.malaysiansydneyfood.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food")
    fun getAll(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM food WHERE title = :titleToQuery")
    fun getByTitle(titleToQuery: String): Flow<List<FoodEntity>>

    @Query("SELECT * FROM food WHERE favorite = 1")
    fun getByFavorite(): Flow<List<FoodEntity>>

    @Update
    fun update(vararg foodEntities:FoodEntity)
}