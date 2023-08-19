package com.ofamosoron.android_workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DayCounterWorker(
    private val applicationContext: Context,
    private val parameters: WorkerParameters
): CoroutineWorker(applicationContext, parameters) {

    private val sharedPreferencesHelper = SharedPreferencesHelper

    override suspend fun doWork(): Result {
        val currentValue = sharedPreferencesHelper.read(applicationContext)
        val counter = currentValue + 1
        val result = sharedPreferencesHelper.write(applicationContext, counter)
        return Result.retry()
    }
}