package com.example.controladministrativo.Views.Grado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.controladministrativo.Controllers.DocenteController
import com.example.controladministrativo.Controllers.GradoSeccionController
import com.example.controladministrativo.Models.Curso
import com.example.controladministrativo.Models.GradoSeccion
import com.example.controladministrativo.R

class GradoActivity : AppCompatActivity() {

    private val gradoSeccionController = GradoSeccionController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grado)

        setupGrados()
        GuardarGradoSeccion()
    }

    //Carga los datos para el Spinner (ComboBox)
    private fun setupGrados() {
        val datosSpinner = listOf("Primero", "Segundo", "Tercero", "Cuarto", "Quinto")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, datosSpinner)
        val spinner = findViewById<Spinner>(R.id.cboGradoSeccion)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }


    private fun GuardarGradoSeccion(){

        val editTextSeccion = findViewById<EditText>(R.id.editTextSeccion)
        val cboGradoSeccion = findViewById<Spinner>(R.id.cboGradoSeccion)
        val guardarCursoButton = findViewById<Button>(R.id.guardarCursoButton)

        guardarCursoButton.setOnClickListener {
            val grado = cboGradoSeccion.selectedItem.toString()
            val seccion = editTextSeccion.text.toString()

            val nuevoGradoSeccion = GradoSeccion(grado, seccion)

            gradoSeccionController.agregarGradoSeccion(nuevoGradoSeccion) { exito ->
                if (exito) {
                    editTextSeccion.setText("")
                    cboGradoSeccion.setSelection(0)
                    Toast.makeText(this, "Grado y Sección agregado correctamente", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Error al agregar Grado y Sección", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}