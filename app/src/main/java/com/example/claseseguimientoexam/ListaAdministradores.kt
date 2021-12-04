package com.example.claseseguimientoexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_lista_admin.*


class ListaAdministradores : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_admin)

        toggle= ActionBarDrawerToggle(this,drawLayout2,R.string.open,R.string.close)
        drawLayout2.addDrawerListener(toggle)
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(toggle.onOptionsItemSelected(item)){return true}
        return super.onOptionsItemSelected(item)
    }

}