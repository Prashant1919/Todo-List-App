package com.example.todo.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.EdittaskBinding

class Adapterdata(private val list: MutableList<Tododata>) :
    RecyclerView.Adapter<Adapterdata.ToDoViewHolder>(){
        private lateinit var listner:dataprovider
        fun setListner(obj:dataprovider){
            this.listner=obj
        }
    inner class ToDoViewHolder(val binding: EdittaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding=EdittaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.task.text=this.task
                binding.edittext.setOnClickListener{
                    listner.edittextdata(this)
                    Log.d("Hi","welcome")
                }
                binding.delete.setOnClickListener {
                    listner.deletedata(this)


                }
            }
        }

    }
    interface dataprovider{
        fun edittextdata(todo:Tododata)
        fun deletedata(todo:Tododata)
    }
}