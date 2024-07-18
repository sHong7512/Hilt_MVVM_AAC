package com.shong.hilt_mvvm_aac.data.preference

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface PrefEntryPoint{
    fun getSettingPref() : SettingPref
}