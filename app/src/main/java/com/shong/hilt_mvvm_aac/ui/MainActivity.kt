package com.shong.hilt_mvvm_aac.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.shong.hilt_mvvm_aac.R
import com.shong.hilt_mvvm_aac.databinding.ActivityMainBinding
import com.shong.hilt_mvvm_aac.handler.LoggerInMemory
import com.shong.hilt_mvvm_aac.navigator.LogTypes
import com.shong.hilt_mvvm_aac.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName + "_sHong"
    private val viewModel : MainViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var loggerMemory: LoggerInMemory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mainVM = viewModel
        binding.lifecycleOwner = this

        binding.logMakeButton.setOnClickListener {
            viewModel.addLogDB("Hello sHong!")
            viewModel.addLogMemory("Hello sHong!")
        }
        binding.logDeleteButton.setOnClickListener {
            viewModel.removeLogDB()
            viewModel.removeLogMemory()
        }
        binding.logDBButton.setOnClickListener {
            navigator.navigateTo(LogTypes.DB)
        }
        binding.logMemoryButton.setOnClickListener {
            navigator.navigateTo(LogTypes.Memory)
        }

        viewModel.updateDBIsOkLD.observe(this, Observer {
            Log.d(TAG,"DB update complete? $it")
        })
        viewModel.removeDBIsOkLD.observe(this, Observer {
            Log.d(TAG,"DB remove complete? $it")
        })
        viewModel.updateMemoryIsOkLD.observe(this, Observer {
            Log.d(TAG,"Memory update complete? $it")
        })
        viewModel.removeMemoryIsOkLD.observe(this, Observer {
            Log.d(TAG,"Memory remove complete? $it")
        })
    }
}