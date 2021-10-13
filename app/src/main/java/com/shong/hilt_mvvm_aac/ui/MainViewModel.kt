package com.shong.hilt_mvvm_aac.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel constructor(private val app : Application) : AndroidViewModel(app) {
    private val TAG = this::class.java.simpleName + "_sHong"


}