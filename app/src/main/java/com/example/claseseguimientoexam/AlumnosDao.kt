package com.example.claseseguimientoexam

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlumnosDao {
    @Query("SELECT * FROM alumnos")
    fun getAll(): LiveData<List<Alumno>>

    @Query("SELECT * FROM alumnos WHERE idAlumno=:id")
    fun get(id: Int): LiveData<Alumno>

    @Query("SELECT * FROM alumnos WHERE email=:email AND pass=:pass")
    fun get(email: String, pass: String): LiveData<Alumno>

    @Insert
    fun insertAll(vararg alumno: Alumno): List<Long>

    @Update
    fun update(alumno:Alumno)

    @Delete
    fun delete(alumno:Alumno)


}