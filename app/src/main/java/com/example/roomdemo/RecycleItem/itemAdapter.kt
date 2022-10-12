package com.example.roomdemo.RecycleItem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import com.example.roomdemo.RoomItem.StudentEntity
import com.example.roomdemo.databinding.ItemViewLayBinding

class itemAdapter(private val items : ArrayList<StudentEntity>,
// new thing
//                private val updateListner : (id:Int)->Unit,
//                private val deleteListner : (id:Int)->Unit
):RecyclerView.Adapter<itemAdapter.ViewHolder>() {

    class ViewHolder(binding : ItemViewLayBinding) : RecyclerView.ViewHolder(binding.root){
        val cardview = binding.laycardview
        val tvName = binding.rvname
        val tvemail = binding.rvemail
        val Editbtn = binding.btnedit
        val Delbtn = binding.btndel


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ItemViewLayBinding.inflate(
           LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.tvName.text = item.name
        holder.tvemail.text = item.email

        //Todo position color change if required context use there vidNo. :- 173

        holder.Editbtn.setOnClickListener {
            Toast.makeText(context,"WORKING ON IT",Toast.LENGTH_SHORT).show()
//            updateListner.invoke(item.id)
        }
        holder.Delbtn.setOnClickListener {
            Toast.makeText(context,"WORKING ON IT",Toast.LENGTH_SHORT).show()
//            deleteListner.invoke(item.id)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}