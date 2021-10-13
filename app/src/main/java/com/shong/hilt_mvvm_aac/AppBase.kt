package com.shong.hilt_mvvm_aac

import android.app.Application
import androidx.room.Room
import com.shong.hilt_mvvm_aac.data.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppBase :Application()