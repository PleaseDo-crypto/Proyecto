package com.example.claseseguimientoexam

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro_carrera.*
import kotlinx.android.synthetic.main.activity_registro_nuevo.*
import kotlinx.android.synthetic.main.activity_registro_nuevo.emailnew
import kotlinx.android.synthetic.main.activity_registro_nuevo.matriculanew
import kotlinx.android.synthetic.main.activity_registro_nuevo.nombrenew
import kotlinx.android.synthetic.main.activity_registro_nuevo.passnew
import kotlinx.android.synthetic.main.activity_registro_nuevo.savefoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistroCarrera : AppCompatActivity() {
    private val SELECT_ACTIVITY = 50
    private var imagealu: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_carrera)
        var idCarrera: Int? = null
        if (intent.hasExtra("carreras")) {

            val carreras = intent.extras?.getSerializable("carreras") as Carreras
            nombre_carrera.setText(carreras.nombrecarr)
            edificio_carrera.setText(carreras.edificio)
            coordinador_carrera.setText(carreras.coordinador)
            cuatrimestre_carrera.setText(carreras.cutrimestre)
            idCarrera = carreras.idCarrera
            //-----------
            val imagecarrera = ImagenController.getImageUri(this, idCarrera.toLong())
            fotocarrera.setImageURI(imagecarrera)

        }
        val database = CarrerasDatabase.getDatabase(this)
        registro_carr.setOnClickListener {
            val carrera = nombre_carrera.text.toString()
            val edificio = edificio_carrera.text.toString()
            val coordinador = coordinador_carrera.text.toString()
            val cuatrimestre = cuatrimestre_carrera.text.toString()

            val regCarreras = Carreras(carrera,R.drawable.ic_launcher_background, edificio, coordinador, cuatrimestre)
            if(idCarrera != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    regCarreras.idCarrera = idCarrera
                    database.carreras().update(regCarreras)
                    imagealu?.let {
                        val intent = Intent()
                        intent.data = it
                        setResult(Activity.RESULT_OK, intent)
                        ImagenController.saveImage(this@RegistroCarrera, idCarrera.toLong(), it)
                    }
                    this@RegistroCarrera.finish()

                }
            }else {

                CoroutineScope(Dispatchers.IO).launch {

                    val id = database.carreras().insertAll(regCarreras)[0]
                    imagealu?.let {
                        ImagenController.saveImage(this@RegistroCarrera, id, it)
                    }
                    this@RegistroCarrera.finish()
                }
            }
        }



        fotocarrera.setOnClickListener {
            ImagenController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imagealu = data!!.data
                fotocarrera.setImageURI(imagealu)
            }
        }
    }
}