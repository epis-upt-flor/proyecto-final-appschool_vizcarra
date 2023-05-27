package com.example.controladministrativo.Models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class Curso(
    val nombre: String,
    val grado: String
)

class CursoModel {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val cursosReference: DatabaseReference = firebaseDatabase.reference.child("Curso")

    fun addCurso(curso: Curso, callback: (Boolean) -> Unit) {
        val newCursoReference = cursosReference.push()
        newCursoReference.setValue(curso)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}