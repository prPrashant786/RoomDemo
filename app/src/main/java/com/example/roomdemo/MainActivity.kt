package com.example.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btadd?.setOnClickListener {
            //TODO add record
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

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}