package com.example.claseseguimientoexam

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "libros")
class Libros(
    val foto_libro:Int,
    val titulo: String,
    val autor: String,
    val genero: String,
    val sinopsis: String,
    @PrimaryKey(autoGenerate = true)
    var idLibro: Int = 0
    ) : Serializable
