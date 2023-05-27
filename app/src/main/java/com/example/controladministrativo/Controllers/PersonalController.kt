package com.example.controladministrativo.Controllers

import com.example.controladministrativo.Models.Personal
import com.example.controladministrativo.Models.PersonalModel

class PersonalController {
    private val docenteModel = PersonalModel()

    fun agregarPersonal(personal: Personal, callback: (Boolean) -> Unit) {
        docenteModel.addDocente(personal, callback)
    }
}