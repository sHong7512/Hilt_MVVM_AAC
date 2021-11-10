

package com.shong.hilt_mvvm_aac.util

import android.annotation.SuppressLint
import dagger.hilt.android.scopes.FragmentScoped
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@FragmentScoped
class LogDateFormatter @Inject constructor() {

    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("d MMM yyyy HH:mm:ss")

    fun formatDate(timestamp: Long): String {
        return formatter.format(Date(timestamp))
    }
}
