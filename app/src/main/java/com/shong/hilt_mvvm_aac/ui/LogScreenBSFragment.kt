package com.shong.hilt_mvvm_aac.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shong.hilt_mvvm_aac.data.db.AppDatabase
import com.shong.hilt_mvvm_aac.databinding.FragmentBottomsheetBinding
import com.shong.hilt_mvvm_aac.handler.LoggerDataSource
import com.shong.hilt_mvvm_aac.handler.LoggerInMemoryDataSource
import com.shong.hilt_mvvm_aac.handler.LoggerLocalDataSource
import com.shong.hilt_mvvm_aac.navigator.LogTypes
import com.shong.hilt_mvvm_aac.util.LogDateFormatter
import com.shong.hilt_mvvm_aac.util.LogsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogScreenBSFragment constructor(private val logType: LogTypes)  : BottomSheetDialogFragment() {
    private val TAG = this::class.java.simpleName + "_sHong"
    private lateinit var binding : FragmentBottomsheetBinding
    private lateinit var logger: LoggerDataSource
    private lateinit var dateFormatter: LogDateFormatter

    private lateinit var logsDatabase : AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBottomsheetBinding.inflate(layoutInflater,container,false)

        binding.logRV.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentView = view.parent as FrameLayout

        val bottomSheetBehavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(parentView)
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.peekHeight = 0

        CoroutineScope(Dispatchers.Main).launch {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        val lp = parentView.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = 900
        parentView.setLayoutParams(lp)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        logsDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "logging.db"
        ).build()

        when(logType){
            LogTypes.DB -> logger = LoggerLocalDataSource(logsDatabase.logDao())
            LogTypes.Memory -> logger = LoggerInMemoryDataSource()
        }
        dateFormatter = LogDateFormatter()
    }

    override fun onResume() {
        super.onResume()

        logger.getAllLogs { logs ->
            binding.logRV.adapter = LogsAdapter(logs, dateFormatter)
            binding.logRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            Log.d(TAG,"memory hash <${logger.hashCode()}> : ${logs}")
        }
    }

}