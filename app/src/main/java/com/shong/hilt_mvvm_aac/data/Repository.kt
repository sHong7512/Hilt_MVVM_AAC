package com.shong.hilt_mvvm_aac.data

import android.content.Context
import androidx.room.Room
import com.shong.hilt_mvvm_aac.data.db.AppDatabase
import com.shong.hilt_mvvm_aac.handler.LoggerDataSource
import com.shong.hilt_mvvm_aac.handler.LoggerLocalDataSource

class Repository constructor(private val context: Context) {
    private var logsDatabase : AppDatabase
    private lateinit var loggerDB: LoggerDataSource

    init {
        logsDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "logging.db"
        ).build()

        loggerDB = LoggerLocalDataSource(logsDatabase.logDao())
    }


}