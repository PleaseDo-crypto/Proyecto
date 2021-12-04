package com.example.claseseguimientoexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.activity_inicio_sistema.*

class InicioSistema : AppCompatActivity() {
    private lateinit var database: AlumnoDatabase
    private lateinit var alumno: Alumno
    private lateinit var alumnoLiveData: LiveData<Alumno>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sistema)

        btn_inLista.setVisibility(View.INVISIBLE)
        btn_listaAdm.setVisibility(View.INVISIBLE)
        btn_carr.setVisibility(View.INVISIBLE)
        btn_carr.setVisibility(View.INVISIBLE)

        database = AlumnoDatabase.getDatabase(this)

        val email = intent.getStringExtra("email")
        val pass = intent.getStringExtra("pass")

        if (email == "") {
            txt_email.text = "Error: Falta Correo!!!"
        } else {
            if (pass == "") {
                txt_email.text = "Error: Falta Contraseña!!!"
            } else {
                alumnoLiveData = database.alumnos().get(email.toString(), pass.toString())
                alumnoLiveData.observe(this, {
                    if (it != null) {
                        alumno = it
                        txt_email.text = "Bienvenido\n" + alumno.nombre
                        btn_inLista.setVisibility(View.VISIBLE)
                        btn_listaAdm.setVisibility(View.VISIBLE)
                        btn_listalib2.setVisibility(View.VISIBLE)
                        btn_carr.setVisibility(View.VISIBLE)

                    } else {
                        txt_email.text =
                            "No Exite el Usuario \n o datos incorretos \n Email o Conraseña"
                    }
                })
            }
        }

        btn_inLista.setOnClickListener {
            val intent = Intent(this, ListaAlumnos::class.java)
            startActivity(intent)
        }

        btn_listaAdm.setOnClickListener {
            val intent = Intent(this, ListaAdministradores::class.java)
            startActivity(intent)
        }
        btn_listalib2.setOnClickListener {
            val intent = Intent(this, ListaLibros::class.java)
            startActivity(intent)
        }

        btn_carr.setOnClickListener {
            val intent = Intent(this, ListaCarreras::class.java)
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