package com.vdc.assignment.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vdc.assignment.repository.db.DBConstant
import com.vdc.assignment.repository.db.entities.ResultEntity

/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resultEntity: ResultEntity): Long

    @Query("SELECT * FROM ${DBConstant.TABLE_RESULT} WHERE searchKey LIKE :searchKey AND searchDate LIKE :date")
    suspend fun findResult(searchKey: String, date: Long): List<ResultEntity>
}