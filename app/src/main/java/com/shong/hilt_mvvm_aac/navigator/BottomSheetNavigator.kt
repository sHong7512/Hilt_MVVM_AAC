package com.shong.hilt_mvvm_aac.navigator

enum class LogTypes {
    DB,
    Memory
}

interface BottomSheetNavigator {
    fun navigateTo(logType: LogTypes)
}