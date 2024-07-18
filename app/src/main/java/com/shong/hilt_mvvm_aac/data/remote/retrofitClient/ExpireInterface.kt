package com.shong.hilt_mvvm_aac.data.remote.retrofitClient

import com.shong.hilt_mvvm_aac.data.remote.model.TimeResponse
import retrofit2.Response
import retrofit2.http.GET

// 앱 시간제한용
interface ExpireInterface {
    @GET("timezone/Etc/UTC")
    suspend fun getCurrentTime(): Response<TimeResponse>
}