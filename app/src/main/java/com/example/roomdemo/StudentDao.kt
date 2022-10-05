package com.example.roomdemo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    /**
     * Suspend KeyWord is Use For using code rootines
     * Because it will take some time to udate insert etc..
     * so we use sunspend to make it on Background thread
     * not on Main thread
     */

    @Insert
    suspend fun insert(studentEntity: StudentEntity)

    @Update
    suspend fun update(studentEntity: StudentEntity)

    @Delete
    suspend fun delete(studentEntity: StudentEntity)

    @Query("SELECT * FROM `Student-table`")
    fun fetchAllStudent():Flow<List<StudentEntity>>
 // FLow Update automaticaly Additional Featere of Courotine

    @Query("SELECT * FROM `Student-table` WHERE id=:id")
    fun fetchStudentById(id:Int):Flow<List<StudentEntity>>

}