package com.mukhash.dodo.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mukhash.dodo.data.DoDoDatabase
import com.mukhash.dodo.data.models.DoDoData
import com.mukhash.dodo.data.repository.DoDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoDoViewModel(application: Application): AndroidViewModel(application) {

    private val doDoDao = DoDoDatabase.getDatabase(application).doDoDao()
    private val repository: DoDoRepository

    val getAllData: LiveData<List<DoDoData>>
    val sortByHighPriority: LiveData<List<DoDoData>>
    val sortByLowPriority: LiveData<List<DoDoData>>

    init {
        repository = DoDoRepository(doDoDao)
        getAllData = repository.getAllData
        sortByHighPriority = repository.sortByHighPriority
        sortByLowPriority = repository.sortByLowPriority
    }

    fun insertData(doDoData: DoDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(doDoData)
        }
    }

    fun updateData(doDoData: DoDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(doDoData)
        }
    }

    fun deleteData(doDoData: DoDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(doDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<DoDoData>> {
        return repository.searchDatabase(searchQuery)
    }

}