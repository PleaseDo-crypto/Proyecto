package com.example.claseseguimientoexam

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro_nuevo.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistroNuevo : AppCompatActivity() {
    private val SELECT_ACTIVITY = 50
    private var imagealu: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_nuevo)

        var idAlumno: Int? = null
        if (intent.hasExtra("alumno")) {

            val alumno = intent.extras?.getSerializable("alumno") as Alumno
            nombrenew.setText(alumno.nombre)
            matriculanew.setText(alumno.matricula.toString())
            emailnew.setText(alumno.email)
            passnew.setText(alumno.pass)
            idAlumno = alumno.idAlumno
            //-----------
            val imagealu = ImagenController.getImageUri(this, idAlumno.toLong())
            savefoto.setImageURI(imagealu)

        }
        val database = AlumnoDatabase.getDatabase(this)
        registrar_btn.setOnClickListener {
            val matricula = matriculanew.text.toString().toInt()
            val nombre = nombrenew.text.toString()
            val email = emailnew.text.toString()
            val pass = passnew.text.toString()

            val regAlumno = Alumno(matricula, R.drawable.ic_launcher_background, nombre, email, pass)
            if(idAlumno != null) {
                CoroutineScope(Dispatchers.IO).launch {
                   regAlumno.idAlumno=idAlumno
                    database.alumnos().update(regAlumno)
                    imagealu?.let {
                        val intent = Intent()
                        intent.data = it
                        setResult(Activity.RESULT_OK, intent)
                        ImagenController.saveImage(this@RegistroNuevo, idAlumno.toLong(), it)
                    }
                    this@RegistroNuevo.finish()

                }
            }else {

                CoroutineScope(Dispatchers.IO).launch {

                    val id = database.alumnos().insertAll(regAlumno)[0]
                    imagealu?.let {
                        ImagenController.saveImage(this@RegistroNuevo, id, it)
                    }
                    this@RegistroNuevo.finish()
                }
            }
        }



        savefoto.setOnClickListener {
            ImagenController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imagealu = data!!.data
                savefoto.setImageURI(imagealu)
            }
        }
    }
}