package com.example.claseseguimientoexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_lista_alumnos.*


class ListaAlumnos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alumnos)

        //---------------------------------------------------------------------------
        var listaAlumnos = emptyList<Alumno>()
        var database = AlumnoDatabase.getDatabase(this)

        database.alumnos().getAll().observe(this, Observer {
            listaAlumnos = it
            val adapter = AlumnoAdapter(this, listaAlumnos)
            alumnoslista.adapter = adapter
        })
        //-----------------------------------------------------------------------------
        alumnoslista.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, AlumnoActivity::class.java)
            intent.putExtra("id", listaAlumnos[position].idAlumno)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.multioptions_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.registrar -> {
                startActivity(Intent(this, RegistroNuevo::class.java))
            }
            R.id.registraradm -> {
                startActivity(Intent(this, NuevoAdmin::class.java))
            }
            R.id.libronuevo -> {
                startActivity(Intent(this, NuevoLibro::class.java))
            }
            R.id.carreranueva -> {
                startActivity(Intent(this, RegistroCarrera::class.java))
            }

            R.id.map -> {
                startActivity(Intent(this, mapafijo::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}