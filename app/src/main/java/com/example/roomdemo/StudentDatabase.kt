package com.example.roomdemo

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase

@Database(entities = [StudentEntity::class], version = 1)
abstract class StudentDatabase:RoomDatabase() {

    abstract fun studentDao() : StudentDao


}