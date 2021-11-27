package com.androidcafe.malaysiansydneyfood.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [FoodEntity::class],
    exportSchema = true)
    //autoMigrations = [AutoMigration (from = 1, to = 2)])
abstract class FoodDatabase : RoomDatabase() {

    abstract val dao: IFoodDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: FoodDatabase

        fun getInstance(context: Context): FoodDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FoodDatabase::class.java,
                        "food_internal.db") // name needs to be different than food.db
                            .createFromAsset("database/food.db")
                            //commented out because it overwrites the previous saved data
                            //.fallbackToDestructiveMigration()
                            .build()

                }
                return INSTANCE
            }
        }
    }
}