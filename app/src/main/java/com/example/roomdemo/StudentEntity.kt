package com.example.roomdemo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Student-table")
data class StudentEntity (
        @PrimaryKey(autoGenerate = true)
        val id : Int = 0,
        val name : String = "",
        val email : String = ""
)