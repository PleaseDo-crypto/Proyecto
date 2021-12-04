package com.example.claseseguimientoexam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_carrera.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CarreraActivity : AppCompatActivity() {
    private lateinit var database: CarrerasDatabase
    private lateinit var carreras: Carreras
    private lateinit var carreraLiveData: LiveData<Carreras>
    private val EDIT_ACTIVITY = 50
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrera)

        database = CarrerasDatabase.getDatabase(this)

        val idCarrera = intent.getIntExtra("id", 0)
        //--------------------------------------------------------------------------------------------
        val imageUri = ImagenController.getImageUri(this, idCarrera.toLong())
        imagedet_carr.setImageURI(imageUri)
        //--------------------------------------------------------------------------------------------

        carreraLiveData = database.carreras().get(idCarrera)

        carreraLiveData.observe(this, Observer {
            carreras = it
            nomcarr_detall.text = carreras.nombrecarr
            edificio_detall.text = carreras.edificio
            coor_detall.text = carreras.coordinador
            cutri_detall.text = carreras.cutrimestre
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carrera, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editar_carrera -> {
                val intent = Intent(this, RegistroCarrera::class.java)
                intent.putExtra("carreras", carreras)
                startActivityForResult(intent, EDIT_ACTIVITY)
            }
            R.id.eliminar_carrera -> {
                carreraLiveData.removeObservers(this)
                CoroutineScope(Dispatchers.IO).launch {
                    database.carreras().delete(carreras)
                    ImagenController.deleteImage(
                        this@CarreraActivity,
                        carreras.idCarrera.toLong()
                    )
                    this@CarreraActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            resultCode == EDIT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imagedet_carr.setImageURI(data!!.data)
            }
        }
    }
}