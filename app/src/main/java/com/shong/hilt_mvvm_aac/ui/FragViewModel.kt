package com.shong.hilt_mvvm_aac.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shong.hilt_mvvm_aac.data.Repository
import com.shong.hilt_mvvm_aac.data.db.AppLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragViewModel @Inject constructor(val repo: Repository) : ViewModel() {
    private val TAG = this::class.java.simpleName + "_sHong"

    var getMemoryLD: MutableLiveData<List<AppLog>> = MutableLiveData()
    fun getLogMemory() {
        try {
            getMemoryLD.value = repo.getAllLogsMemory()
        } catch (e: Exception) {
            getMemoryLD.value = listOf()
            Log.d(TAG, "$e")
        }
    }

    var getDBLD: MutableLiveData<List<AppLog>> = MutableLiveData()
    fun getLogDB() {
        viewModelScope.launch {
            try {
                getDBLD.value = repo.getAllLogs()
            } catch (e: Exception) {
                getDBLD.value = listOf()
            }
        }
    }
}