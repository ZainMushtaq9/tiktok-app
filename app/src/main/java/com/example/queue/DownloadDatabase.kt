package com.example.queue

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "downloads")
data class DownloadTask(
    @PrimaryKey val url: String,
    val platform: String, // TIKTOK, INSTAGRAM, YOUTUBE, FACEBOOK
    val title: String,
    val status: String, // waiting, downloading, paused, retrying, completed, failed
    val progress: Float = 0f,
    val retryCount: Int = 0,
    val dateAdded: Long = System.currentTimeMillis()
)

@Dao
interface DownloadDao {
    @Query("SELECT * FROM downloads ORDER BY dateAdded ASC")
    fun getAllTasks(): Flow<List<DownloadTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(task: DownloadTask)

    @Query("UPDATE downloads SET status = :status, progress = :progress WHERE url = :url")
    suspend fun updateStatus(url: String, status: String, progress: Float)

    @Query("SELECT * FROM downloads WHERE status = 'waiting' OR status = 'retrying' ORDER BY dateAdded ASC LIMIT 1")
    suspend fun getNextPendingTask(): DownloadTask?
}

@Database(entities = [DownloadTask::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun downloadDao(): DownloadDao
}
