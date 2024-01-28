package com.example.todo.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.R
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.util.Adapterdata
import com.example.todo.util.Tododata
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.HashSet


class Home : Fragment(), Adapterdata.dataprovider, todopopup.Data {

    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: FragmentHomeBinding
    private var pop: todopopup? = null

    //List and adapter is created for recycle view
    private lateinit var adapter: Adapterdata
    private lateinit var mlist: MutableList<Tododata>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        taskaddition()
        getdata()
    }


    private fun init(view: View) {
        //Initialize obj
        auth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)
        databaseReference = FirebaseDatabase.getInstance().reference.child("task")
            .child(auth.currentUser?.uid.toString())
        binding.recycleview.setHasFixedSize(true)
        binding.recycleview.layoutManager = LinearLayoutManager(context)
        mlist = mutableListOf()
        adapter = Adapterdata(mlist)
        adapter.setListner(this)
        binding.recycleview.adapter = adapter


    }

    private fun taskaddition() {
        binding.fbbtn.setOnClickListener {
            //This method is used to null object or reference of object
            if (pop != null)
                childFragmentManager.beginTransaction().remove(pop!!).commit()
            pop = todopopup()
            pop!!.show(childFragmentManager, todopopup.TAG)
            pop!!.setlitner(this)


        }
    }

    private fun getdata() {
        //Added data in list from database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mlist.clear()
                // fetch data
                for (snap in snapshot.children) {
                    val todotask = snap.key?.let {
                        Tododata(it, snap.value.toString())
                    }
                    if (todotask != null) {
                        mlist.add(todotask)

                    }

                }
                adapter.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun savedata(taskdata: String, task: TextInputEditText) {
        //Data is added to database
        databaseReference.push().setValue(taskdata).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Task added successfully", Toast.LENGTH_LONG).show()
                task.text = null

            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
            }
            pop!!.dismiss()
        }

    }


    override fun updatetask(todo: Tododata, task: TextInputEditText) {
//Update data in database
        val map = HashMap<String, Any>()
        map[todo.taskid] = todo.task

        databaseReference.updateChildren(map).addOnCompleteListener({
            if (it.isSuccessful) {
                Toast.makeText(context, "Data Update sucessfully", Toast.LENGTH_LONG).show()
                task.text = null
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
            }
            task.text = null
            pop?.dismiss()
        })


    }


    override fun edittextdata(todo: Tododata) {
        //Display dialog box editing textfield
        if (pop != null) {
            childFragmentManager.beginTransaction().remove(pop!!).commit()
        }

        pop = todopopup.newinstance(todo.taskid, todo.task)
        pop!!.setlitner(this)
        pop!!.show(childFragmentManager, todopopup.TAG)


    }

    override fun deletedata(todo: Tododata) {
        //Delete data from database
        databaseReference.child(todo.taskid).removeValue().addOnCompleteListener({
            if (it.isSuccessful) {
                adapter.notifyDataSetChanged()
                Toast.makeText(context, "Task delete successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
            }
        })

    }


}