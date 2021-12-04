package com.example.claseseguimientoexam

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "alumnos")
class Alumno(
    val matricula: Int,
    val foto: Int,
    val nombre: String,
    val email: String,
    val pass: String,
    @PrimaryKey(autoGenerate = true)
    var idAlumno: Int = 0
    ) : Serializable
