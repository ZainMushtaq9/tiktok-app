package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.data.AppDatabase
import com.example.data.QueueItem
import com.example.data.QueueRepository
import com.example.data.QueueStatus
import com.example.worker.DownloadWorker
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QueueViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: QueueRepository
    private val workManager = WorkManager.getInstance(application)

    val uiState: StateFlow<List<QueueItem>>

    init {
        val queueDao = AppDatabase.getDatabase(application).queueDao()
        repository = QueueRepository(queueDao)
        uiState = repository.allItems.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun addDownloadTask(url: String, filename: String) {
        viewModelScope.launch {
            val item = QueueItem(videoUrl = url, filename = filename)
            val id = repository.insert(item).toInt()
            enqueueDownloadWork(id)
        }
    }

    private fun enqueueDownloadWork(itemId: Int) {
        val data = Data.Builder()
            .putInt(DownloadWorker.KEY_ITEM_ID, itemId)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(data)
            .setConstraints(constraints)
            .build()

        workManager.enqueue(request)
    }
    
    fun removeTask(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}
