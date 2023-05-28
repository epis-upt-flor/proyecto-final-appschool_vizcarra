package com.example.controladministrativo.Views.Docentes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import com.example.controladministrativo.Controllers.DocenteController
import com.example.controladministrativo.Controllers.PersonalController
import com.example.controladministrativo.Models.Docente
import com.example.controladministrativo.Models.Personal
import com.example.controladministrativo.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DocenteActivity : AppCompatActivity() {

    private val docenteController = DocenteController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_docente)

        GuardarDocente()
    }

    private fun GuardarDocente(){
        val editTextDNIDocente = findViewById<EditText>(R.id.editTextDNIDocente)
        val editTextCorreoDocente = findViewById<EditText>(R.id.editTextCorreoDocente)
        val editTextNombreDocente = findViewById<EditText>(R.id.editTextNombreDocente)
        val editTextApellidoDocente= findViewById<EditText>(R.id.editTextApellidoDocente)
        val editTextDireccionDocente = findViewById<EditText>(R.id.editTextDireccionDocente)
        val editTextCelularDocente = findViewById<EditText>(R.id.editTextCelularDocente)
        val rboMasculinoDocente = findViewById<RadioButton>(R.id.rboMasculinoDocente)
        val rboFemeninoDocente = findViewById<RadioButton>(R.id.rboFemeninoDocente)
        val guardarDocenteButton = findViewById<Button>(R.id.guardarDocenteButton)
        val rboGrupoDocente = findViewById<RadioGroup>(R.id.rboGrupoDocente)

        val auth = Firebase.auth

        guardarDocenteButton.setOnClickListener{
            val dni = editTextDNIDocente.text.toString()
            val correo = editTextCorreoDocente.text.toString()
            val nombre = editTextNombreDocente.text.toString()
            val apellido = editTextApellidoDocente.text.toString()
            val direccion = editTextDireccionDocente.text.toString()
            val celular = editTextCelularDocente.text.toString()
            var sexo: String = ""

            if (rboMasculinoDocente.isChecked){
                sexo = rboMasculinoDocente.text.toString()
            }else{
                sexo = rboFemeninoDocente.text.toString()
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
                                        val nuevoDocente = Docente(
                                            dni, correo, nombre, apellido,
                                            direccion, celular, sexo
                                        )
                                        docenteController.agregarDocente(nuevoDocente) { exito ->
                                            if (exito) {
                                                editTextDNIDocente.setText("")
                                                editTextCorreoDocente.setText("")
                                                editTextNombreDocente.setText("")
                                                editTextApellidoDocente.setText("")
                                                editTextDireccionDocente.setText("")
                                                editTextCelularDocente.setText("")
                                                rboGrupoDocente.clearCheck()
                                                Toast.makeText(
                                                    this,
                                                    "Docente agregado correctamente",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    this,
                                                    "Error al agregar el Docente",
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