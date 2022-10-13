package com.example.roomdemo

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.roomdemo.RecycleItem.itemAdapter
import com.example.roomdemo.RoomItem.StudentDao
import com.example.roomdemo.RoomItem.StudentEntity
import com.example.roomdemo.RoomItem.studentApp
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.databinding.DialogeUpdateBinding
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
            val itemAdapter = itemAdapter(studentList ,
                {
                    updateId ->
                    updateRecordDialoge(updateId,studentDao)
                },
                {
                    deleteId ->
                    deleteRecordAlertDialog(deleteId,studentDao)
                }
                )

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

    fun updateRecordDialoge(id : Int,studentDao: StudentDao){
        val updateDialog = Dialog(this, com.google.android.material.R.style.Theme_AppCompat_Dialog)
        updateDialog.setCancelable(false)

        updateDialog.setTitle("Update Record")

        val binding = DialogeUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch {
            studentDao.fetchStudentById(id).collect{
                binding.udname.setText(it.name); //Set data
                binding.udemail.setText(it.email)
            }
        }

        binding.update.setOnClickListener {
            val name = binding.udname.text.toString()
            val email = binding.udemail.text.toString()

            if (email.isNotEmpty() && name.isNotEmpty()){
                lifecycleScope.launch {

                    studentDao.update(StudentEntity(id ,name,email))

                    Toast.makeText(applicationContext,"UPDATED",Toast.LENGTH_LONG).show()

                    updateDialog.dismiss()
                }
            }
            else {
                Toast.makeText(applicationContext,"ENTER THE DATA",Toast.LENGTH_LONG).show()
            }
        }

        binding.cancel.setOnClickListener {
            updateDialog.dismiss()
        }

        updateDialog.show()
    }

    fun deleteRecordAlertDialog(id : Int,studentDao: StudentDao){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setCancelable(false)

        builder.setPositiveButton("Yes"){ dialogInterface, _ ->
            lifecycleScope.launch {
            studentDao.delete(StudentEntity(id))
                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                dialogInterface.dismiss() // Dialog will be dismissed
            }
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }

        val alertDialog: AlertDialog = builder.create()

        alertDialog.show()
    }
    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}