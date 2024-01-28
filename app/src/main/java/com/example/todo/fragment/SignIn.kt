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
import com.google.firebase.auth.FirebaseAuth


class SignIn : Fragment() {
  private lateinit var auth:FirebaseAuth
  private lateinit var navController: NavController
  private lateinit var binding:FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          init(view)
           validatedata()
           nextactivity()
    }

    private fun nextactivity() {
        binding.sign.setOnClickListener {
            navController.navigate(R.id.action_signIn_to_signup)
        }
    }

    private fun validatedata() {
        binding.nextBtn.setOnClickListener {
            val email=binding.email.text.toString().trim()
            val password=binding.password.text.toString().trim()
            if(email.isNotEmpty()&&password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener({
                if(it.isSuccessful){
                    Toast.makeText(context,"Login successfully",Toast.LENGTH_LONG).show()
                    navController.navigate(R.id.action_signIn_to_home2)
                }
                else{
                    Toast.makeText(context,it.exception?.message,Toast.LENGTH_LONG).show()
                }
            })
            }
            else
            {
                if(email.isEmpty()){
                    binding.email.error="Field cannot empty"
                }
                else if(password.isEmpty()){
                    binding.password.error="Field cannot empty"
                }
                else
                {
                    binding.email.error="Field cannot empty"
                    binding.password.error="Field cannot empty"
                }


            }



        }
    }
    private fun init(view: View) {
        navController=Navigation.findNavController(view)
        auth=FirebaseAuth.getInstance()
    }


}