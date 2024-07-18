package com.shong.hilt_mvvm_aac

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp

fun Any.logD(msg: String) = Log.d("${this::class.java.simpleName}_sHong", msg)
fun Any.logW(msg: String) = Log.w("${this::class.java.simpleName}_sHong", msg)
fun Any.logE(msg: String) = Log.e("${this::class.java.simpleName}_sHong", msg)

private var toast: Toast? = null
fun Context.toast(message: String) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast?.show()
}

@HiltAndroidApp
class AppBase : Application()