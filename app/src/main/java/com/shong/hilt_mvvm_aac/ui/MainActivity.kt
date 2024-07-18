package com.shong.hilt_mvvm_aac.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shong.hilt_mvvm_aac.R
import com.shong.hilt_mvvm_aac.databinding.ActivityMainBinding
import com.shong.hilt_mvvm_aac.logD
import com.shong.hilt_mvvm_aac.logW
import com.shong.hilt_mvvm_aac.navigator.LogTypes
import com.shong.hilt_mvvm_aac.navigator.Navigator
import com.shong.hilt_mvvm_aac.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //    private val viewModel: MainViewModel by viewModels()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val binding: ActivityMainBinding by lazy {
        // ActivityMainBinding.inflate(layoutInflater)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    @Inject
    lateinit var navigator: Navigator

    private lateinit var expireJob: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            binding.mainVM = viewModel
            binding.lifecycleOwner = this@MainActivity

            logDBButton.setOnClickListener {
                navigator.navigateTo(LogTypes.DB)
            }
            logMemoryButton.setOnClickListener {
                navigator.navigateTo(LogTypes.Memory)
            }
        }

        makeObserver()
    }

    override fun onDestroy() {
        expireJob.cancel()
        super.onDestroy()
    }

    private fun makeObserver() {
        viewModel.updateDBIsOkLD.observe(this, Observer {
            logD("DB update complete? $it")
        })
        viewModel.removeDBIsOkLD.observe(this, Observer {
            logD("DB remove complete? $it")
        })
        viewModel.updateMemoryIsOkLD.observe(this, Observer {
            logD("Memory update complete? $it")
        })
        viewModel.removeMemoryIsOkLD.observe(this, Observer {
            logD("Memory remove complete? $it")
        })

        val expireDateTime = LocalDateTime.of(2024, 7, 1, 0, 0, 0)
        val koreaZoneId = ZoneId.of("Asia/Seoul")
        val zonedDateTime: ZonedDateTime = expireDateTime.atZone(koreaZoneId)
        val unixTimeExpire: Instant = zonedDateTime.toInstant()
        val expireUnixTime = unixTimeExpire.epochSecond
        expireJob = CoroutineScope(Dispatchers.Main).launch {
            viewModel.expireFlow.collect {
                val currentUnix = it?.unixTime ?: (System.currentTimeMillis() / 1000)
                if (currentUnix > expireUnixTime) {
                    logW("데모 사용 기간 종료")
                    toast("데모 사용 기간 종료")
                    finishAffinity()
                }
            }
        }
    }
}