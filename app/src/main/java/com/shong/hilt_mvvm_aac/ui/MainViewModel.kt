package com.shong.hilt_mvvm_aac.ui

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.provider.Settings
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shong.hilt_mvvm_aac.data.Repository
import com.shong.hilt_mvvm_aac.data.preference.SettingPref
import com.shong.hilt_mvvm_aac.data.remote.model.TimeResponse
import com.shong.hilt_mvvm_aac.logD
import com.shong.hilt_mvvm_aac.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repository,
    val app: Application,
    var settingPref: SettingPref
) :
    AndroidViewModel(app) {
    companion object {
        private const val MAX_BASE = 1
    }

    var updateDBIsOkLD: MutableLiveData<Boolean> = MutableLiveData()
    var removeDBIsOkLD: MutableLiveData<Boolean> = MutableLiveData()
    var updateMemoryIsOkLD: MutableLiveData<Boolean> = MutableLiveData()
    var removeMemoryIsOkLD: MutableLiveData<Boolean> = MutableLiveData()

    private val _expireFlow: MutableSharedFlow<TimeResponse?> = MutableSharedFlow(
        replay = MAX_BASE,
        extraBufferCapacity = MAX_BASE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val expireFlow: SharedFlow<TimeResponse?> = _expireFlow.asSharedFlow()

    var titleStr: ObservableField<String> = ObservableField("MVVM-AAC Hilt Example")
    fun logMake() {
        viewModelScope.launch {
            try {
                repo.addLog("<DB> hello sHong!")
                updateDBIsOkLD.value = true
            } catch (e: Exception) {
                updateDBIsOkLD.value = false
            }
        }
        try {
            repo.addLogMemory("<Memory> hello sHong!")
            updateMemoryIsOkLD.value = true
        } catch (e: Exception) {
            updateMemoryIsOkLD.value = false
        }
    }

    fun logDel() {
        viewModelScope.launch {
            try {
                repo.removeLogs()
                removeDBIsOkLD.value = true
            } catch (e: Exception) {
                removeDBIsOkLD.value = false
            }
        }
        try {
            repo.removeLogsMemory()
            removeMemoryIsOkLD.value = true
        } catch (e: Exception) {
            removeMemoryIsOkLD.value = false
        }
    }

    fun startExpireOnClick() {
        viewModelScope.launch {
            try {
                _expireFlow.emit(repo.getInternetTime())
            } catch (e: Exception) {
                _expireFlow.emit(null)
            }
        }
    }

    fun uniqueCheckOnClick() {
        var uniqueId = settingPref.getUniqueID()
        if (uniqueId.isEmpty()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                uniqueId = UUID.randomUUID().toString()
            } else {
                @SuppressLint("HardwareIds")
                uniqueId =
                    Settings.Secure.getString(app.contentResolver, Settings.Secure.ANDROID_ID)
            }
            settingPref.setUniqueID(uniqueId)
        }
        logD("unique id :: $uniqueId")
    }

}