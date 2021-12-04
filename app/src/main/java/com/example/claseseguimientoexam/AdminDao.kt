package com.example.claseseguimientoexam

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AdminDao {
    @Query("SELECT * FROM administradores")
    fun getAll(): LiveData<List<Administradores>>

    @Query("SELECT * FROM administradores WHERE idadmin=:id")
    fun get(id: Int): LiveData<Administradores>

    @Insert
    fun insertAll(vararg administradores: Administradores): List<Long>

    @Update
    fun update(administradores: Administradores)

    @Delete
    fun delete(administradores: Administradores)
}