package com.mukhash.dodo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mukhash.dodo.data.models.DoDoData

@Dao
interface DoDoDao {

    @Query("SELECT * FROM dodo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<DoDoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(doDoData: DoDoData)

    @Update
    suspend fun updateData(doDoData: DoDoData)

    @Delete
    suspend fun deleteData(doDoData: DoDoData)

    @Query("DELETE FROM dodo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM dodo_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<DoDoData>>

    @Query("SELECT * FROM dodo_table ORDER BY CASE WHEN priority LIKE 'H%' then 1 when priority like 'M%' then 2 when priority like 'L%' then 3 end")
    fun sortByHighPriority(): LiveData<List<DoDoData>>

    @Query("SELECT * FROM dodo_table ORDER BY CASE WHEN priority LIKE 'H%' then 3 when priority like 'M%' then 2 when priority like 'L%' then 1 end")
    fun sortByLowPriority(): LiveData<List<DoDoData>>

}