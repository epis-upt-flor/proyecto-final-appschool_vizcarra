package com.example.controladministrativo.Models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class Personal(
    val dni: String,
    val correo: String,
    val nombre: String,
    val apellidos: String,
    val direccion: String,
    val celular: String,
    val rol: String,
    var sexo: String
)
class PersonalModel {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val personalReference: DatabaseReference = firebaseDatabase.reference.child("Personal")

    fun addDocente(personal: Personal, callback: (Boolean) -> Unit) {
        val newPersonalReference = personalReference.push()
        newPersonalReference.setValue(personal)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}