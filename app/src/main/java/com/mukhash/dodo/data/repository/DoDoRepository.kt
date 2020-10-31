package com.mukhash.dodo.data.repository

import androidx.lifecycle.LiveData
import com.mukhash.dodo.data.DoDoDao
import com.mukhash.dodo.data.models.DoDoData

class DoDoRepository(private val doDoDao: DoDoDao) {

    val getAllData: LiveData<List<DoDoData>> = doDoDao.getAllData()
    val sortByHighPriority: LiveData<List<DoDoData>> = doDoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<DoDoData>> = doDoDao.sortByLowPriority()

    suspend fun insertData(doDoData: DoDoData) {
        doDoDao.insertData(doDoData)
    }

    suspend fun updateData(doDoData: DoDoData) {
        doDoDao.updateData(doDoData)
    }

    suspend fun deleteData(doDoData: DoDoData) {
        doDoDao.deleteData(doDoData)
    }

    suspend fun deleteAll() {
        doDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<DoDoData>> {
        return doDoDao.searchDatabase(searchQuery)
    }

}