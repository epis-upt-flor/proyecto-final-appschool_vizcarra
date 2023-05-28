package com.example.controladministrativo.Views.Personal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import com.example.controladministrativo.Controllers.CursoController
import com.example.controladministrativo.Controllers.PersonalController
import com.example.controladministrativo.Models.Personal
import com.example.controladministrativo.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PersonalActivity : AppCompatActivity() {

    private val personalController = PersonalController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        setupRoles()
        GuardarPersonal()
    }

    //Carga los datos para el Spinner (ComboBox)
    private fun setupRoles() {
        val datosSpinner = listOf("Director", "SubDirector", "Secretaria")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, datosSpinner)
        val spinner = findViewById<Spinner>(R.id.cboRolPersonal)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun GuardarPersonal(){
        val editTextDNIPersonal = findViewById<EditText>(R.id.editTextDNIPersonal)
        val editTextCorreoPersonal = findViewById<EditText>(R.id.editTextCorreoPersonal)
        val editTextNombrePersonal = findViewById<EditText>(R.id.editTextNombrePersonal)
        val editTextApellidoPersonal = findViewById<EditText>(R.id.editTextApellidoPersonal)
        val editTextDireccionPersonal = findViewById<EditText>(R.id.editTextDireccionPersonal)
        val editTextCelularPersonal = findViewById<EditText>(R.id.editTextCelularPersonal)
        val cboRolPersonal = findViewById<Spinner>(R.id.cboRolPersonal)
        val rboMasculinoPersonal = findViewById<RadioButton>(R.id.rboMasculinoPersonal)
        val rboFemeninoPersonal = findViewById<RadioButton>(R.id.rboFemeninoPersonal)
        val guardarPersonalButton = findViewById<Button>(R.id.guardarPersonalButton)
        val rboGrupoPersonal = findViewById<RadioGroup>(R.id.rboGrupoPersonal)

        val auth = Firebase.auth

        guardarPersonalButton.setOnClickListener{
            val dni = editTextDNIPersonal.text.toString()
            val correo = editTextCorreoPersonal.text.toString()
            val nombre = editTextNombrePersonal.text.toString()
            val apellido = editTextApellidoPersonal.text.toString()
            val direccion = editTextDireccionPersonal.text.toString()
            val celular = editTextCelularPersonal.text.toString()
            val rol = cboRolPersonal.selectedItem.toString()
            var sexo: String = ""

            if (rboMasculinoPersonal.isChecked){
                sexo = rboMasculinoPersonal.text.toString()
            }else{
                sexo = rboFemeninoPersonal.text.toString()
            }

            // Verificar si el correo ya existe en Firebase Authentication
            auth.fetchSignInMethodsForEmail(correo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val signInMethods = task.result?.signInMethods
                        if (signInMethods.isNullOrEmpty()) {
                            // El correo no está asociado a ninguna cuenta, se puede crear un nuevo usuario

                            // Crear el usuario en Firebase Authentication
                            val contrasenia = generarContrasenia(nombre, apellido, dni)
                            auth.createUserWithEmailAndPassword(correo, contrasenia)
                                .addOnCompleteListener { createTask ->
                                    if (createTask.isSuccessful) {
                                        // Guardar los datos del personal en Firebase Realtime Database
                                        val nuevoPersonal = Personal(
                                            dni, correo, nombre, apellido,
                                            direccion, celular, rol, sexo
                                        )
                                        personalController.agregarPersonal(nuevoPersonal) { exito ->
                                            if (exito) {
                                                editTextDNIPersonal.setText("")
                                                editTextCorreoPersonal.setText("")
                                                editTextNombrePersonal.setText("")
                                                editTextApellidoPersonal.setText("")
                                                editTextDireccionPersonal.setText("")
                                                editTextCelularPersonal.setText("")
                                                cboRolPersonal.setSelection(0)
                                                rboGrupoPersonal.clearCheck()
                                                Toast.makeText(
                                                    this,
                                                    "Personal agregado correctamente",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    this,
                                                    "Error al agregar el Personal",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Error al crear el usuario",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            // El correo ya está asociado a una cuenta existente
                            Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Error al verificar el correo
                        Toast.makeText(this, "Error al verificar el correo", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    //Genera la contraseña con el primer caracter del nombre mas el primer apellido y el primer caracter del DNI
    private fun generarContrasenia(nombre: String, apellido: String, dni: String): String {
        val primerCaracterNombre = nombre.first().toString()
        val primerApellido = apellido.split(" ").first().toString()
        val primerCaracterDNI = dni.first().toString()

        return primerCaracterNombre + primerApellido + primerCaracterDNI
    }
}