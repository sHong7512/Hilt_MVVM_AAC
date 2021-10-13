package com.shong.hilt_mvvm_aac.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.shong.hilt_mvvm_aac.R
import com.shong.hilt_mvvm_aac.data.db.AppDatabase
import com.shong.hilt_mvvm_aac.databinding.ActivityMainBinding
import com.shong.hilt_mvvm_aac.handler.LoggerDataSource
import com.shong.hilt_mvvm_aac.handler.LoggerInMemoryDataSource
import com.shong.hilt_mvvm_aac.handler.LoggerLocalDataSource
import com.shong.hilt_mvvm_aac.navigator.BottomSheetNavigator
import com.shong.hilt_mvvm_aac.navigator.BottomSheetNavigatorImpl
import com.shong.hilt_mvvm_aac.navigator.LogTypes


class MainActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName + "_sHong"
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val navigator: BottomSheetNavigator by lazy { BottomSheetNavigatorImpl(this) }

    private lateinit var loggerDB: LoggerDataSource
    private lateinit var loggerMemory: LoggerDataSource
    private lateinit var logsDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mainVM = viewModel
        binding.lifecycleOwner = this

        logsDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "logging.db"
        ).build()
        loggerDB = LoggerLocalDataSource(logsDatabase.logDao())
        loggerMemory = LoggerInMemoryDataSource()

        binding.logMakeButton.setOnClickListener {
            loggerDB.addLog("Hello sHong!<DB>")
            loggerMemory.addLog("Hello sHong!<Memory>")
        }

        binding.logDeleteButton.setOnClickListener {
            loggerDB.removeLogs()
            loggerMemory.removeLogs()
        }

        binding.logDBButton.setOnClickListener {
            navigator.navigateTo(LogTypes.DB)
        }

        binding.logMemoryButton.setOnClickListener {
            //DI module을 적용해야 함
            navigator.navigateTo(LogTypes.Memory)

            loggerMemory.getAllLogs { logs ->
                Log.d(TAG,"memory hash <${loggerMemory.hashCode()}> : ${logs}")
            }

        }
    }
}