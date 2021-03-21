package com.vdc.assignment.repository.db.dao

import androidx.room.*
import com.vdc.assignment.repository.db.DBConstant
import com.vdc.assignment.repository.db.entities.ForecastDataEntity

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@Dao
interface ForecastDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: ForecastDataEntity)

    @Query("SELECT * FROM ${DBConstant.TABLE_FORECAST} WHERE resultId LIKE :resultId")
    suspend fun findByResultId(resultId: Long): List<ForecastDataEntity>
}