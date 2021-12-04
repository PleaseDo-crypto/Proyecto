package com.example.claseseguimientoexam

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nuevo_libro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoLibro : AppCompatActivity() {
    private val SELECT_ACTIVITY =  30
    private var imagelibro: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_libro)

            var idLibro: Int? = null

            if (intent.hasExtra("libros")) {
                val libros = intent.extras?.getSerializable("libros") as Libros
                titilolibro.setText(libros.titulo)
                generolibro.setText(libros.genero)
                autorlibro.setText(libros.autor)
                sinopsislibro.setText(libros.sinopsis)
                idLibro = libros.idLibro
                //-----------
                val imagelibro = ImagenController.getImageUri(this, idLibro.toLong())
                foto_carrera.setImageURI(imagelibro)

            }

            val database = LibrosDatabase.getDatabase(this)
        registrar_libro.setOnClickListener {
                val titulo = titilolibro.text.toString()
                val autor = autorlibro.text.toString()
                val genero = generolibro.text.toString()
                val sinopsis = sinopsislibro.text.toString()

                val libros = Libros(R.drawable.placeholder, titulo, autor, genero, sinopsis)
                if (idLibro != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        libros.idLibro = idLibro
                        database.libros().update(libros)
                        imagelibro?.let {
                            val intent = Intent()
                            intent.data = it
                            setResult(Activity.RESULT_OK, intent)
                            ImagenController.saveImage(this@NuevoLibro, idLibro.toLong(), it)
                        }
                        this@NuevoLibro.finish()

                    }
                } else {

                    CoroutineScope(Dispatchers.IO).launch {

                        val id = database.libros().insertAll(libros)[0]
                        imagelibro?.let {
                            ImagenController.saveImage(this@NuevoLibro, id, it)
                        }
                        this@NuevoLibro.finish()
                    }
                }

            }

            foto_carrera.setOnClickListener {
                ImagenController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            when {
                requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                    imagelibro = data!!.data
                    foto_carrera.setImageURI(imagelibro)
                }
            }
        }
    }
