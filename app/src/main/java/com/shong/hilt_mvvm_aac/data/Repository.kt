package com.shong.hilt_mvvm_aac.data

import com.shong.hilt_mvvm_aac.data.db.AppLog
import com.shong.hilt_mvvm_aac.data.db.LogDao
import com.shong.hilt_mvvm_aac.handler.LoggerInMemory

class Repository constructor(val logDao: LogDao, val loggerInMemory: LoggerInMemory) {
    private val TAG = this::class.java.simpleName + "_sHong"
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