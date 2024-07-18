package com.shong.hilt_mvvm_aac.data.remote.model

import com.google.gson.annotations.SerializedName

data class TimeResponse(
    @SerializedName("unixtime")
    val unixTime: Long,
)