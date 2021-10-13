package com.shong.hilt_mvvm_aac.navigator

enum class LogTypes {
    DB,
    Memory
}

interface Navigator {
    fun navigateTo(logType: LogTypes)
}