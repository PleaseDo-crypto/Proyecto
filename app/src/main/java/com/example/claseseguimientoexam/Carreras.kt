package com.example.claseseguimientoexam

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "carreras")
class Carreras(
    val nombrecarr:String,
    val fotocarr: Int,
    val edificio: String,
    val coordinador: String,
    val cutrimestre: String,
    @PrimaryKey(autoGenerate = true)
    var idCarrera: Int = 0
    ) : Serializable
