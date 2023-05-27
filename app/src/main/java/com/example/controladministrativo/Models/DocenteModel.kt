package com.example.controladministrativo.Models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class Docente(
    val dni: String,
    val correo: String,
    val nombre: String,
    val apellidos: String,
    val direccion: String,
    val celular: String,
    val sexo: String
)

class DocenteModel {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val cursosReference: DatabaseReference = firebaseDatabase.reference.child("Docente")

    fun addDocente(docente: Docente, callback: (Boolean) -> Unit) {
        val newDocenteReference = cursosReference.push()
        newDocenteReference.setValue(docente)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}