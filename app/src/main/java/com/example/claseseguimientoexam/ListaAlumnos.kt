package com.example.claseseguimientoexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_lista_alumnos.*


class ListaAlumnos : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alumnos)
        //

        toggle= ActionBarDrawerToggle(this,drawLayout,R.string.open,R.string.close)
        drawLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.login-> Toast.makeText(applicationContext,"Login selecionado", Toast.LENGTH_SHORT).show()
                R.id.inicio-> Toast.makeText(applicationContext,"Inicio selecionado", Toast.LENGTH_SHORT).show()
                R.id.mapa-> Toast.makeText(applicationContext,"Mapa selecionado", Toast.LENGTH_SHORT).show()

            }
            true
        }
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(toggle.onOptionsItemSelected(item)){return true}
        return super.onOptionsItemSelected(item)
    }

}