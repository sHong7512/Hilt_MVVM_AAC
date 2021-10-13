package com.shong.hilt_mvvm_aac.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shong.hilt_mvvm_aac.databinding.FragmentBottomsheetBinding
import com.shong.hilt_mvvm_aac.navigator.LogTypes
import com.shong.hilt_mvvm_aac.util.LogsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogScreenBSFragment constructor(private val logType: LogTypes) : BottomSheetDialogFragment() {
    private val TAG = this::class.java.simpleName + "_sHong"
    private val viewModel: FragViewModel by viewModels()

    private lateinit var binding: FragmentBottomsheetBinding
    @Inject lateinit var logsAdapter: LogsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentBottomsheetBinding.inflate(layoutInflater, container, false)

        binding.logRV.setHasFixedSize(true)
        makeObserver()

        when (logType) {
            LogTypes.DB -> viewModel.getLogDB()
            LogTypes.Memory -> viewModel.getLogMemory()
        }

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

        binding.logRV.apply {
            adapter = logsAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun makeObserver() {
        viewModel.getDBLD.observe(this, { logs ->
            logsAdapter.updateLogsData(logs)
        })
        viewModel.getMemoryLD.observe(this, { logs ->
            logsAdapter.updateLogsData(logs)
        })
    }

}