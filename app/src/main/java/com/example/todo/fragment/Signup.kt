package com.example.todo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todo.R
import com.example.todo.databinding.FragmentSignInBinding
import com.example.todo.databinding.FragmentSignupBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException


class Signup : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var navcontroller: NavController
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        validation()
        Nextactivity()

    }

    private fun Nextactivity(){
        binding.In.setOnClickListener {
            navcontroller.navigate(R.id.action_signup_to_signIn)
        }
    }


    private fun validation() {
        binding.nextbt.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirm = binding.confirmpass.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()) {
                if(password.length<=5){
                    Toast.makeText(context,"Password must greater than 5 charcter",Toast.LENGTH_LONG).show()
                }
                else {
                    if (password.equals(confirm)) {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener({
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Register Successfully", Toast.LENGTH_LONG)
                                    .show()
                                navcontroller.navigate(R.id.action_signup_to_home2)
                            } else {
                                Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG)
                                    .show()
                            }
                        })
                    } else {
                        Toast.makeText(context, "Passsword doesn't match ", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }

           else{
                if(email.isEmpty()&& password.isEmpty() && confirm.isEmpty()) {
                    binding.email.error = "Please enter Field"
                    binding.password.error = "Please enter field"
                    binding.confirmpass.error = "Please eneter field"


                }
                else if(email.isNotEmpty())
                {
                    binding.password.error="Please enter Field"
                }
                else if(password.isEmpty())
                {
                  binding.password.error="Please enter field"
                }
                else
                {
                    binding.confirmpass.error="Please enter field"
                }



            }

        }
    }

    private fun init(view: View) {
        navcontroller = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()


    }


}