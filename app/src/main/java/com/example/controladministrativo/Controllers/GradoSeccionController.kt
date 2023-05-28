package com.example.controladministrativo.Controllers

import com.example.controladministrativo.Models.GradoSeccion
import com.example.controladministrativo.Models.GradoSeccionModel

class GradoSeccionController {
    private val GradoSeccionModel = GradoSeccionModel()

    fun agregarGradoSeccion(grado: GradoSeccion, callback: (Boolean) -> Unit) {
        GradoSeccionModel.addGradoSeccion(grado, callback)
    }
}