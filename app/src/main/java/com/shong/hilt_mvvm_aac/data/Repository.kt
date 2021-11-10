package com.shong.hilt_mvvm_aac.data

import android.content.Context
import com.shong.hilt_mvvm_aac.data.db.AppLog
import com.shong.hilt_mvvm_aac.data.db.LogDao
import com.shong.hilt_mvvm_aac.handler.LoggerInMemory
import com.shong.hilt_mvvm_aac.util.EntryPointExample
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

class Repository constructor(val logDao: LogDao, val loggerInMemory: LoggerInMemory, val context: Context) {
    private val TAG = this::class.java.simpleName + "_sHong"

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ExInterface{
        fun getSettingPreferences() : EntryPointExample
    }
    val example: EntryPointExample
    init{
        val ex = EntryPoints.get(context, ExInterface::class.java)
        example = ex.getSettingPreferences()
    }

    suspend fun addLog(msg: String) {
        logDao.insertAllDB(
            AppLog(
                msg,
                System.currentTimeMillis()
            )
        )
    }

    suspend fun getAllLogs() = logDao.getAllDB()

    suspend fun removeLogs() {
        logDao.nukeTableDB()
    }

    fun addLogMemory(msg: String){
        loggerInMemory.addLog(msg)
    }

    fun getAllLogsMemory() = loggerInMemory.getAllLogs()

    fun removeLogsMemory(){ loggerInMemory.removeLogs() }
}