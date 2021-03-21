package com.vdc.assignment.repository.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vdc.assignment.repository.db.DBConstant


/**
 * Created by Huan.Huynh on 18/03/2021
 *
 * Copyright © 2021 Huan.Huynh. All rights reserved.
 */
@Entity(tableName = DBConstant.TABLE_RESULT)
class ResultEntity(
    val searchKey: String? = null,
    val searchDate: Long? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}