package com.example.claseseguimientoexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_lista_carreras.*

class ListaCarreras : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_carreras)

        //
        //---------------------------------------------------------------------------
        var listaCarreras = emptyList<Carreras>()
        var database = CarrerasDatabase.getDatabase(this)

        database.carreras().getAll().observe(this, Observer {
            listaCarreras = it
            val adapter = CarrerasAdapter(this, listaCarreras)
            listacarrera.adapter = adapter
        })
        //-----------------------------------------------------------------------------
        listacarrera.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, CarreraActivity::class.java)
            intent.putExtra("id", listaCarreras[position].idCarrera)
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
