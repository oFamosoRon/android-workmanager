package com.ofamosoron.android_workmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ofamosoron.android_workmanager.ui.theme.AndroidworkmanagerTheme
import java.time.Duration

class MainActivity : ComponentActivity() {

    private lateinit var workManager: WorkManager
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val workRequest = PeriodicWorkRequestBuilder<DayCounterWorker>(
            repeatInterval = Duration.ofDays(1)
        ).build()

        viewModel.updateWorkId(workRequest.id)
        workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueue(workRequest)

        setContent {
            AndroidworkmanagerTheme {
                workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this) {
                    val isActive = it.state
                    viewModel.updateWorkerState(isActive, applicationContext)
                }
                val state = viewModel.state.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "worker Id:")
                        Text(text = "${state.value.workId}")
                        Spacer(modifier = Modifier.padding(12.dp))
                        Text(text = "Modifier state: ${state.value.workerState}")
                        Spacer(modifier = Modifier.padding(12.dp))
                        Text(text = "Today is ${state.value.workValue}")
                        Spacer(modifier = Modifier.padding(18.dp))
                        Button(onClick = {
                            workManager.cancelAllWork()
                        }) {
                            Text(text = "Cancel Work")
                        }

                    }
                }
            }
        }
    }
}