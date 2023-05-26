package com.example.controladministrativo.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.controladministrativo.R
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider: String) {
        title = "Inicio"

        val emailTextView = findViewById<TextView>(R.id.textNombreApellido)
        //val providerTextView = findViewById<TextView>(R.id.providerTextView)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        emailTextView.text = email
        //providerTextView.text = provider

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}