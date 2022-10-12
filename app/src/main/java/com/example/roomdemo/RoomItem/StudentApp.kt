package com.example.roomdemo.RoomItem

import android.app.Application
import com.example.roomdemo.RoomItem.StudentDatabase

class studentApp:Application() {

    val db by lazy {
        StudentDatabase.getInstance(this)
    }
}