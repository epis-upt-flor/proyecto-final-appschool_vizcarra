package com.example.controladministrativo.Controllers

import com.example.controladministrativo.Models.Alumno
import com.example.controladministrativo.Models.AlumnoModel


class AlumnoController {
    private val alumnoModel = AlumnoModel()

    fun agregarAlumno(alumno: Alumno, callback: (Boolean) -> Unit) {
        alumnoModel.addAlumno(alumno, callback)
    }
}