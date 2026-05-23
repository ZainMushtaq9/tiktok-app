package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

enum class QueueStatus {
    WAITING, DOWNLOADING, PAUSED, COMPLETED, FAILED, RETRYING, CANCELLED, NETWORK_WAITING
}

@Entity(tableName = "download_queue")
@Serializable
data class QueueItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val videoUrl: String,
    val filename: String,
    val progress: Float = 0f,
    val retryCount: Int = 0,
    val status: String = QueueStatus.WAITING.name,
    val localPath: String? = null,
    val timestamps: Long = System.currentTimeMillis(),
    val errorLogs: String? = null
)
