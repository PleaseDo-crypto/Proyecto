package com.example.claseseguimientoexam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Carreras::class],version = 1)
abstract class CarrerasDatabase:RoomDatabase(){
    abstract fun carreras():CarrerasDao

    companion object{
        @Volatile
        private var INSTANCE:CarrerasDatabase? = null

        fun getDatabase(context: Context):CarrerasDatabase{
            var tempinstance = INSTANCE

            if(tempinstance != null){
                return tempinstance

            }
            synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarrerasDatabase::class.java,
                    "carrera_database"
                ).build()
                INSTANCE= instance
                return instance
            }
        }
    }



}