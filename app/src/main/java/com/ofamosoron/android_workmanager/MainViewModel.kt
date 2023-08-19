package com.ofamosoron.android_workmanager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class MainViewModel: ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun updateCounter(context: Context) {
        val days = SharedPreferencesHelper.read(context)
        _state.value = _state.value.copy(workValue = days)
    }

    fun updateWorkId(workId: UUID) {
        _state.value = _state.value.copy(workId = workId)
    }

    fun updateWorkerState(workState: WorkInfo.State, context: Context) {
        val days = SharedPreferencesHelper.read(context)
        _state.value = _state.value.copy(workerState = workState, workValue = days)
    }
}

data class State(
    val workId: UUID? = null,
    val workValue: Int = 0,
    val workerState: WorkInfo.State? = null
)