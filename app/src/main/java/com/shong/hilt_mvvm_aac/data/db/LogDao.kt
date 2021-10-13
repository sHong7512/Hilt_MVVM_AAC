package com.shong.hilt_mvvm_aac.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Data access object to query the database.
 */
@Dao
interface LogDao {
    @Query("SELECT * FROM logs ORDER BY id DESC")
    suspend fun getAllDB(): List<AppLog>

    @Insert
    suspend fun insertAllDB(vararg logs: AppLog)

    @Query("DELETE FROM logs")
    suspend fun nukeTableDB()
}
