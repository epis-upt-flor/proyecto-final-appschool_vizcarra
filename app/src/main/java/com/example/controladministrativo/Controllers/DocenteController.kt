package com.example.controladministrativo.Controllers

import com.example.controladministrativo.Models.Docente
import com.example.controladministrativo.Models.DocenteModel

class DocenteController {
    private val docenteModel = DocenteModel()

    fun agregarDocente(docente: Docente, callback: (Boolean) -> Unit) {
        docenteModel.addDocente(docente, callback)
    }
}