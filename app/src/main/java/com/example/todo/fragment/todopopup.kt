package com.example.todo.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todo.R
import com.example.todo.databinding.FragmentTodopopupBinding
import com.example.todo.util.Tododata
import com.google.android.material.textfield.TextInputEditText

class todopopup : DialogFragment() {

private lateinit var binding:FragmentTodopopupBinding
private lateinit var listner:Data
private var todo:Tododata ?=null
    fun setlitner(obj:Data){
        this.listner=obj

    }
    companion object{
        const val TAG="todopopup"
       @JvmStatic
       fun newinstance(taskId:String,task:String)=todopopup().apply{
           arguments=Bundle().apply {
               putString("taskid",taskId)
               putString("task",task)
           }

       }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentTodopopupBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments!=null){
            todo= Tododata(arguments?.getString("taskid").toString() ,
                arguments?.getString("task").toString()
            )
            binding.task.setText(todo?.task)
        }
        takedata()

    }


    private fun takedata() {
        binding.nextpop.setOnClickListener {
            val taskdata=binding.task.text.toString()

            if(taskdata.isNotEmpty()){
                if(todo==null){
                    listner.savedata(taskdata,binding.task)
                }else
                {

                    todo?.task=taskdata
                    listner.updatetask(todo!!,binding.task)
                }

            }
            else
            {
                Toast.makeText(context,"Please Type  task",Toast.LENGTH_LONG).show()
            }
        }
        binding.close.setOnClickListener {
            dismiss()
        }

    }
    interface Data {
        //abstract method to passing data
        fun savedata(taskdata: String, task: TextInputEditText)
        fun updatetask(todo:Tododata,task:TextInputEditText)
    }

    }


