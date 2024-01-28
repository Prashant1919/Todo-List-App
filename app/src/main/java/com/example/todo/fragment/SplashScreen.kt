package com.example.todo.fragment

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todo.R
import com.google.firebase.auth.FirebaseAuth
import java.util.logging.Handler


class SplashScreen : Fragment() {
private lateinit var auth:FirebaseAuth
private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        delayed()

        }
    private fun init(view:View) {
        navController=Navigation.findNavController(view)
        auth=FirebaseAuth.getInstance()
    }

    private fun delayed() {
        android.os.Handler(Looper.myLooper()!!).postDelayed(Runnable {
            if(auth.currentUser !=null) {
                navController.navigate(R.id.action_splashScreen_to_home2)
            }
            else {
                navController.navigate(R.id.action_splashScreen_to_signIn)
            }
        },5000)
    }
}







