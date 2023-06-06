package com.example.controladministrativo.Views.Cursos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.controladministrativo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListCursoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_curso)

        val btnNuevoCurso: FloatingActionButton = findViewById(R.id.btnNuevoCurso)
        btnNuevoCurso.setOnClickListener {

            val intent = Intent(this, CursoActivity::class.java)
            startActivity(intent)
        }
    }



}