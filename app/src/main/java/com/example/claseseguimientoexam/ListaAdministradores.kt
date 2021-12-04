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
import kotlinx.android.synthetic.main.activity_lista_admin.*


class ListaAdministradores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_admin)

        //---------------------------------------------------------------------------
        var listaAdministradores = emptyList<Administradores>()
        var database = AdminDatabase.getDatabase(this)

        database.administradores().getAll().observe(this, Observer {
            listaAdministradores = it
            val adapter = AdminAdapter(this, listaAdministradores)
            lista_adm.adapter = adapter
        })
        //-----------------------------------------------------------------------------
        lista_adm.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("id", listaAdministradores[position].idAdmin)
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