package com.shong.hilt_mvvm_aac.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shong.hilt_mvvm_aac.data.Repository
import com.shong.hilt_mvvm_aac.navigator.LogTypes
import com.shong.hilt_mvvm_aac.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repo: Repository) : ViewModel() {
    private val TAG = this::class.java.simpleName + "_sHong"

    var titleStr: ObservableField<String> = ObservableField("MVVM-AAC Hilt Example")

    fun logMake(){
        addLogDB("Hello sHong!")
        addLogMemory("Hello sHong!")
    }
    fun logDel() {
        removeLogDB()
        removeLogMemory()
    }

    var updateDBIsOkLD: MutableLiveData<Boolean> = MutableLiveData()
    fun addLogDB(msg: String) {
        viewModelScope.launch {
            try {
                repo.addLog(msg + "<DB>")
                updateDBIsOkLD.value = true
            } catch (e: Exception) {
                updateDBIsOkLD.value = false
            }
        }
    }

    var removeDBIsOkLD: MutableLiveData<Boolean> = MutableLiveData()
    fun removeLogDB() {
        viewModelScope.launch {
            try {
                repo.removeLogs()
                removeDBIsOkLD.value = true
            } catch (e: Exception) {
                removeDBIsOkLD.value = false
            }
        }
    }

    var updateMemoryIsOkLD: MutableLiveData<Boolean> = MutableLiveData()
    fun addLogMemory(msg: String) {
        try {
            repo.addLogMemory(msg + "<Memory>")
            updateMemoryIsOkLD.value = true
        } catch (e: Exception) {
            updateMemoryIsOkLD.value = false
        }
    }

    var removeMemoryIsOkLD: MutableLiveData<Boolean> = MutableLiveData()
    fun removeLogMemory() {
        try {
            repo.removeLogsMemory()
            removeMemoryIsOkLD.value = true
        } catch (e: Exception) {
            removeMemoryIsOkLD.value = false
        }
    }

}