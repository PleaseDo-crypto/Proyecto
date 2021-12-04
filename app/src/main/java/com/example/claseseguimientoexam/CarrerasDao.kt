package com.example.claseseguimientoexam

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarrerasDao {
    @Query("SELECT * FROM carreras")
    fun getAll(): LiveData<List<Carreras>>

    @Query("SELECT * FROM carreras WHERE idCarrera = :id")
    fun get(id: Int): LiveData<Carreras>

    @Insert
    fun insertAll(vararg carreras: Carreras): List<Long>

    @Update
    fun update(carreras: Carreras)

    @Delete
    fun delete(carreras: Carreras)


}