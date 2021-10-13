package com.shong.hilt_mvvm_aac.handler

import com.shong.hilt_mvvm_aac.data.db.AppLog
import dagger.Provides
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.LinkedList
import javax.inject.Inject
import javax.inject.Singleton

class LoggerInMemory constructor(){
    private val logs = LinkedList<AppLog>()

    fun addLog(msg: String) {
        logs.addFirst(AppLog(msg, System.currentTimeMillis()))
    }

    fun getAllLogs() = logs

    fun removeLogs() {
        logs.clear()
    }
}
