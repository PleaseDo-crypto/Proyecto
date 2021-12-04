package com.example.claseseguimientoexam

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LibrosDao {

    @Query("SELECT * FROM libros")
    fun getAll(): LiveData<List<Libros>>

    @Query("SELECT * FROM libros WHERE idLibro = :id")
    fun get(id: Int): LiveData<Libros>

    @Insert
    fun insertAll(vararg libros: Libros): List<Long>

    @Update
    fun update(libros: Libros)

    @Delete
    fun delete(libros: Libros)
}