package com.example.controladministrativo.Models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class GradoSeccion(
    val grado: String,
    val seccion: String
)


class GradoSeccionModel {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val cursosReference: DatabaseReference = firebaseDatabase.reference.child("Grado")

    fun addGradoSeccion(grado: GradoSeccion, callback: (Boolean) -> Unit) {
        val newGradoSeccionReference = cursosReference.push()
        newGradoSeccionReference.setValue(grado)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}