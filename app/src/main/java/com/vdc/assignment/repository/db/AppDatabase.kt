package com.vdc.assignment.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vdc.assignment.repository.db.dao.ForecastDataDao
import com.vdc.assignment.repository.db.dao.ResultDao
import com.vdc.assignment.repository.db.entities.ForecastDataEntity
import com.vdc.assignment.repository.db.entities.ResultEntity


/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@Database(
    entities = [
        ResultEntity::class,
        ForecastDataEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun resultDao(): ResultDao

    abstract fun weatherDao(): ForecastDataDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context, dbName: String): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context, dbName).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, dbName: String): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
                .build()
        }
    }
}