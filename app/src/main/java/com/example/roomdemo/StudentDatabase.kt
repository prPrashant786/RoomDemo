package com.example.roomdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudentEntity::class], version = 1)
abstract class StudentDatabase:RoomDatabase() {

    abstract fun studentDao() : StudentDao //Dao Class

    companion object    { // It allowed add function to student data
        @Volatile
        private var INSTANCE : StudentDatabase? = null;

        fun getInstance(context: Context):StudentDatabase{
            synchronized(this){
                var instance = INSTANCE;
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "employee_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


}