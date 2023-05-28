package com.example.controladministrativo.Views.Cursos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.controladministrativo.Controllers.CursoController
import com.example.controladministrativo.Models.Curso
import com.example.controladministrativo.R
import com.example.controladministrativo.Views.Docentes.DocenteActivity

class CursoActivity : AppCompatActivity() {

    private val cursoController = CursoController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curso)

        setupGrados()
        GuardarCurso()
    }

    //Carga los datos para el Spinner (ComboBox)
    private fun setupGrados() {
        val datosSpinner = listOf("Primero", "Segundo", "Tercero", "Cuarto", "Quinto")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, datosSpinner)
        val spinner = findViewById<Spinner>(R.id.cboGradoCurso)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun GuardarCurso(){

        val editTextNombreCurso = findViewById<EditText>(R.id.editTextNombreCurso)
        val cboGradoCurso = findViewById<Spinner>(R.id.cboGradoCurso)
        val guardarCursoButton = findViewById<Button>(R.id.guardarCursoButton)

        guardarCursoButton.setOnClickListener {
            val nombre = editTextNombreCurso.text.toString()
            val grado = cboGradoCurso.selectedItem.toString()

            val nuevoCurso = Curso(nombre, grado)

            cursoController.agregarCurso(nuevoCurso) { exito ->
                if (exito) {
                    editTextNombreCurso.setText("")
                    cboGradoCurso.setSelection(0)
                    Toast.makeText(this, "Curso agregado correctamente", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Error al agregar el Curso", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}