package com.example.data

import kotlinx.coroutines.flow.Flow

class QueueRepository(private val queueDao: QueueDao) {
    val allItems: Flow<List<QueueItem>> = queueDao.getAllItems()
    val pendingItems: Flow<List<QueueItem>> = queueDao.getPendingItems()

    suspend fun insert(item: QueueItem): Long = queueDao.insertItem(item)

    suspend fun update(item: QueueItem) = queueDao.updateItem(item)

    suspend fun updateStatus(id: Int, status: QueueStatus) = queueDao.updateStatus(id, status.name)

    suspend fun deleteById(id: Int) = queueDao.deleteItemById(id)
}
