package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roomdemo.RecycleItem.itemAdapter
import com.example.roomdemo.RoomItem.StudentDao
import com.example.roomdemo.RoomItem.StudentEntity
import com.example.roomdemo.RoomItem.studentApp
import com.example.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val studentDao = (application as studentApp).db.studentDao()
        binding?.btadd?.setOnClickListener {
            addRecord(studentDao)
        }

        lifecycleScope.launch {
            studentDao.fetchAllStudent().collect{// get student list
                val list = ArrayList(it)

                setupListOfDataIntoRecycleview(list,studentDao)
            }
        }

    }

    fun addRecord(studentDao: StudentDao){
        val name = binding?.etname?.text.toString()
        val email = binding?.etemail?.text.toString()

        if (email.isNotEmpty() && name.isNotEmpty()){
            lifecycleScope.launch {

                studentDao.insert(StudentEntity(name = name, email = email))

                Toast.makeText(applicationContext,"DATA SAVED",Toast.LENGTH_LONG).show()
                binding?.etname?.text?.clear()
                binding?.etemail?.text?.clear()
            }
        }else{
            Toast.makeText(applicationContext,"ENTER THE DATA",Toast.LENGTH_LONG).show()
        }
    }

    private fun setupListOfDataIntoRecycleview(
        studentList : ArrayList<StudentEntity>,
    studentDao: StudentDao){
        if (!studentList.isEmpty()){
            val itemAdapter = itemAdapter(studentList)

            binding?.rvdetails?.layoutManager = GridLayoutManager(this,2)
            binding?.rvdetails?.adapter = itemAdapter

            binding?.rvdetails?.visibility = View.VISIBLE
            binding?.notext?.visibility =View.GONE

        }
        else{
            binding?.rvdetails?.visibility = View.GONE
            binding?.notext?.visibility =View.VISIBLE
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}