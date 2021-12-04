package com.example.claseseguimientoexam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_libro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibroActivity : AppCompatActivity() {
    private lateinit var database: LibrosDatabase
    private lateinit var libros: Libros
    private lateinit var libroLiveData: LiveData<Libros>
    private val EDIT_ACTIVITY = 30
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)

        database = LibrosDatabase.getDatabase(this)

        val idLibro = intent.getIntExtra("id", 0)
        //--------------------------------------------------------------------------------------------
        val imagelibro = ImagenController.getImageUri(this, idLibro.toLong())
        foto_carrera.setImageURI(imagelibro)
        //--------------------------------------------------------------------------------------------

        libroLiveData = database.libros().get(idLibro)

        libroLiveData.observe(this, Observer {
            libros = it
            titulodetall.text = libros.titulo
            autordetall.text = libros.autor
            generodeall.text = libros.genero
            sinopsisdetall.text = libros.sinopsis
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_libros, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editar_libro -> {
                val intent = Intent(this, NuevoLibro::class.java)
                intent.putExtra("libros", libros)
                startActivityForResult(intent, EDIT_ACTIVITY)
            }
            R.id.eliminar_libro -> {
                libroLiveData.removeObservers(this)
                CoroutineScope(Dispatchers.IO).launch {
                    database.libros().delete(libros)
                    ImagenController.deleteImage(
                        this@LibroActivity,
                        libros.idLibro.toLong()
                    )
                    this@LibroActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            resultCode == EDIT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                foto_carrera.setImageURI(data!!.data)
            }
        }
    }
}