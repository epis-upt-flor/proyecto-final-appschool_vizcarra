package com.example.controladministrativo.Controllers

import com.example.controladministrativo.Models.Curso
import com.example.controladministrativo.Models.CursoModel

class CursoController {
    private val cursoModel = CursoModel()

    fun agregarCurso(curso: Curso, callback: (Boolean) -> Unit) {
        cursoModel.addCurso(curso, callback)
    }
}