package com.shong.hilt_mvvm_aac.data

import android.content.Context
import com.shong.hilt_mvvm_aac.data.db.AppLog
import com.shong.hilt_mvvm_aac.data.db.LogDao
import com.shong.hilt_mvvm_aac.data.remote.model.TimeResponse
import com.shong.hilt_mvvm_aac.data.remote.retrofitClient.ExpireInterface
import com.shong.hilt_mvvm_aac.data.remote.retrofitClient.ExpireRetrofitApi
import com.shong.hilt_mvvm_aac.handler.LoggerInMemory
import com.shong.hilt_mvvm_aac.util.EntryPointExample
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay

class Repository constructor(
    val context: Context,
    val logDao: LogDao,
    val loggerInMemory: LoggerInMemory,
    expireRetrofitApi: ExpireRetrofitApi
) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface ExInterface {
        fun getEntryPointExample(): EntryPointExample
    }

    private val example: EntryPointExample

    init {
        val ex = EntryPoints.get(context, ExInterface::class.java)
        example = ex.getEntryPointExample()
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

    fun addLogMemory(msg: String) {
        loggerInMemory.addLog(msg)
    }

    fun getAllLogsMemory() = loggerInMemory.getAllLogs()

    fun removeLogsMemory() {
        loggerInMemory.removeLogs()
    }

    private val epInterface: ExpireInterface = expireRetrofitApi.createClient()

    suspend fun getInternetTime(): TimeResponse? {
        delay(2000)
        return epInterface.getCurrentTime().body()
    }
}