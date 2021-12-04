package com.example.claseseguimientoexam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alumno::class],version = 1)
abstract class AlumnoDatabase:RoomDatabase(){
    abstract fun alumnos():AlumnosDao

    companion object{
        @Volatile
        private var INSTANCE:AlumnoDatabase? = null

        fun getDatabase(context: Context):AlumnoDatabase{
            var tempinstance = INSTANCE

            if(tempinstance != null){
                return tempinstance

            }
            synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlumnoDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE= instance
                return instance
            }
        }
    }



}