package com.shong.hilt_mvvm_aac.handler

import com.shong.hilt_mvvm_aac.data.db.AppLog
import java.util.*

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
