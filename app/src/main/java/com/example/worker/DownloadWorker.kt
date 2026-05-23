package com.example.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.AppDatabase
import com.example.data.QueueStatus
import kotlinx.coroutines.delay

class DownloadWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val itemId = inputData.getInt(KEY_ITEM_ID, -1)
        if (itemId == -1) {
            return Result.failure()
        }

        val database = AppDatabase.getDatabase(applicationContext)
        val dao = database.queueDao()

        dao.updateStatus(itemId, QueueStatus.DOWNLOADING.name)
        
        try {
            // Simulate download progress
            for (i in 1..100) {
                delay(50) // Fake network delay
                
                // You could periodically update DB with progress here
                // For simplicity we'll just wait
            }

            dao.updateStatus(itemId, QueueStatus.COMPLETED.name)
            return Result.success()
        } catch (e: Exception) {
            dao.updateStatus(itemId, QueueStatus.FAILED.name)
            return Result.retry()
        }
    }

    companion object {
        const val KEY_ITEM_ID = "ITEM_ID"
    }
}
