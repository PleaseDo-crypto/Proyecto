package com.example.claseseguimientoexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_lista_alumnos.*
import kotlinx.android.synthetic.main.activity_lista_alumnos.navView
import kotlinx.android.synthetic.main.activity_lista_carreras.*

class ListaCarreras : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_carreras)
        //

        toggle= ActionBarDrawerToggle(this,drawLayout4,R.string.open,R.string.close)
        drawLayout4.addDrawerListener(toggle)
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(toggle.onOptionsItemSelected(item)){return true}
        return super.onOptionsItemSelected(item)
    }

}