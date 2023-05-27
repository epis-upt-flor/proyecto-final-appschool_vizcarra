package com.example.controladministrativo.Models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


data class Alumno(
    val dni: String,
    val correo: String,
    val nombre: String,
    val apellidos: String,
    val direccion: String,
    val celular: String,
    val edad: Int,
    val sexo: String
)


class AlumnoModel {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val cursosReference: DatabaseReference = firebaseDatabase.reference.child("Alumno")

    fun addAlumno(alumno: Alumno, callback: (Boolean) -> Unit) {
        val newAlumnoReference = cursosReference.push()
        newAlumnoReference.setValue(alumno)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}