package com.example.claseseguimientoexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_lista_libros.*


class ListaLibros : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_libros)

        toggle= ActionBarDrawerToggle(this,drawLayout3,R.string.open,R.string.close)
        drawLayout3.addDrawerListener(toggle)
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
        var listaLibros = emptyList<Libros>()
        var database = LibrosDatabase.getDatabase(this)

        database.libros().getAll().observe(this, Observer {
            listaLibros = it
            val adapter = LibrosAdapter(this, listaLibros)
            listalibros.adapter = adapter
        })
        //-----------------------------------------------------------------------------
        listalibros.setOnItemClickListener{ parent, view, position, id ->
            val intent = Intent(this, LibroActivity::class.java)
            intent.putExtra("id", listaLibros[position].idLibro)
            startActivity(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(toggle.onOptionsItemSelected(item)){return true}
        return super.onOptionsItemSelected(item)
    }

}