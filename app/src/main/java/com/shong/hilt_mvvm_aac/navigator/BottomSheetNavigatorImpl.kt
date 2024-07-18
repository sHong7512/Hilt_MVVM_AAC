package com.shong.hilt_mvvm_aac.navigator

import androidx.fragment.app.FragmentActivity
import com.shong.hilt_mvvm_aac.ui.LogScreenBSFragment
import javax.inject.Inject

class BottomSheetNavigatorImpl @Inject constructor(private val activity: FragmentActivity) :
    Navigator {
    override fun navigateTo(logType: LogTypes) {
        val bottomSheet = LogScreenBSFragment(logType = logType)

        bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
    }
}