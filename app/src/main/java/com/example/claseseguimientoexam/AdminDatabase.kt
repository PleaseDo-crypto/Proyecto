package com.example.claseseguimientoexam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Administradores::class],version = 1)
 abstract class AdminDatabase : RoomDatabase(){

     abstract fun administradores(): AdminDao

    companion object{
        @Volatile
        private var INSTANCE:AdminDatabase? = null

        fun getDatabase(context: Context):AdminDatabase{
            var tempinstance = INSTANCE

            if(tempinstance != null){
                return tempinstance

            }
            synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    AdminDatabase::class.java,
                    "admin_database"
                ).build()
                INSTANCE= instance
                return instance
            }
        }
    }
}