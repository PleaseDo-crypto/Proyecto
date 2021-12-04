package com.example.claseseguimientoexam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_alumno.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlumnoActivity : AppCompatActivity() {
    private lateinit var database: AlumnoDatabase
    private lateinit var alumno: Alumno
    private lateinit var alumnoLiveData: LiveData<Alumno>
    private val EDIT_ACTIVITY = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumno)

        database = AlumnoDatabase.getDatabase(this)

        val idAlumno = intent.getIntExtra("id", 0)
        //--------------------------------------------------------------------------------------------
        val imageUri = ImagenController.getImageUri(this, idAlumno.toLong())
        fotodetall.setImageURI(imageUri)
        //--------------------------------------------------------------------------------------------

        alumnoLiveData = database.alumnos().get(idAlumno)

        alumnoLiveData.observe(this, Observer {
            alumno = it
            nombredetall.text = alumno.nombre
            matriculadetall.text = alumno.matricula.toString()
            emaildetall.text = alumno.email
            passdetall.text = alumno.pass
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.alumno_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update_item -> {
                val intent = Intent(this, RegistroNuevo::class.java)
                intent.putExtra("alumno", alumno)
                startActivityForResult(intent, EDIT_ACTIVITY)
            }
            R.id.delete_item -> {
                alumnoLiveData.removeObservers(this)
                CoroutineScope(Dispatchers.IO).launch {
                    database.alumnos().delete(alumno)
                    ImagenController.deleteImage(
                        this@AlumnoActivity,
                        alumno.idAlumno.toLong()
                    )
                    this@AlumnoActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            resultCode == EDIT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                fotodetall.setImageURI(data!!.data)
            }
        }
    }
}