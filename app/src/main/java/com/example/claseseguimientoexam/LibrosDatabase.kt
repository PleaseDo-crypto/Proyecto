package com.example.claseseguimientoexam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Libros::class], version = 1)
abstract class LibrosDatabase : RoomDatabase() {

    abstract fun libros(): LibrosDao

    companion object{
        @Volatile
        private var INSTANCE:LibrosDatabase? = null

        fun getDatabase(context: Context):LibrosDatabase{
            var tempinstance = INSTANCE

            if(tempinstance != null){
                return tempinstance

            }
            synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibrosDatabase::class.java,
                    "libro_database"
                ).build()
                INSTANCE= instance
                return instance
            }
        }
    }
}