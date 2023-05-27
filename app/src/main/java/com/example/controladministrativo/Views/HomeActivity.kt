package com.example.controladministrativo.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.controladministrativo.R
import com.example.controladministrativo.Views.Alumnos.AlumnoActivity
import com.example.controladministrativo.Views.Cursos.CursoActivity
import com.example.controladministrativo.Views.Docentes.DocenteActivity
import com.example.controladministrativo.Views.Personal.PersonalActivity
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

        setupClickListeners()


    }

    //Obtiene los datos del login
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

    //Cambio de vistas
    private fun setupClickListeners() {

        val vistaPersonal = findViewById<LinearLayout>(R.id.vistaPersonal)
        vistaPersonal.setOnClickListener {
            navigateToActivityPersonal()
        }

        val vistaAlumno = findViewById<LinearLayout>(R.id.vistaAlumnos)
        vistaAlumno.setOnClickListener {
            navigateToActivityAlumno()
        }

        val vistaDocente = findViewById<LinearLayout>(R.id.vistaDocentes)
        vistaDocente.setOnClickListener {
            navigateToActivityDocente()
        }

        val vistaCurso = findViewById<LinearLayout>(R.id.vistaCursos)
        vistaCurso.setOnClickListener {
            navigateToActivityCurso()
        }
    }

    //Vista Personal
    private fun navigateToActivityPersonal() {
        val intent = Intent(this@HomeActivity, PersonalActivity::class.java)
        startActivity(intent)
    }

    //Vista Alumno
    private fun navigateToActivityAlumno() {
        val intent = Intent(this@HomeActivity, AlumnoActivity::class.java)
        startActivity(intent)
    }


    //Vista Docente
    private fun navigateToActivityDocente() {
        val intent = Intent(this@HomeActivity, DocenteActivity::class.java)
        startActivity(intent)
    }

    //Vista Curso
    private fun navigateToActivityCurso() {
        val intent = Intent(this@HomeActivity, CursoActivity::class.java)
        startActivity(intent)
    }
}