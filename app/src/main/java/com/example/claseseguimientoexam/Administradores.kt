package com.example.claseseguimientoexam

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "administradores")
class Administradores(
    val imagen_admin: Int,
    val nombre_admin:String,
    val permisos_admin:String,
    val email_admin:String,
    val contraseña_admin:String,
    @PrimaryKey(autoGenerate = true)
    var idAdmin: Int = 0
    ): Serializable