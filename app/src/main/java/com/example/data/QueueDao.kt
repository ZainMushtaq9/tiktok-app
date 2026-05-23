package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QueueDao {
    @Query("SELECT * FROM download_queue ORDER BY timestamps DESC")
    fun getAllItems(): Flow<List<QueueItem>>

    @Query("SELECT * FROM download_queue WHERE status IN ('WAITING', 'RETRYING', 'NETWORK_WAITING') ORDER BY timestamps ASC")
    fun getPendingItems(): Flow<List<QueueItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: QueueItem): Long

    @Update
    suspend fun updateItem(item: QueueItem)

    @Query("UPDATE download_queue SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String)

    @Query("DELETE FROM download_queue WHERE id = :id")
    suspend fun deleteItemById(id: Int)
}
