package com.example.claseseguimientoexam

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AdminActivity : AppCompatActivity() {
    private lateinit var database: AdminDatabase
    private lateinit var administradores: Administradores
    private lateinit var administradoresLiveData: LiveData<Administradores>
    private val EDIT_ACTIVITY = 49


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        database = AdminDatabase.getDatabase(this)

        val idAdmin = intent.getIntExtra("id", 1)
        //--------------------------------------------------------------------------------------------
        val imageUri = ImagenController.getImageUri(this, idAdmin.toLong(),)
        fotoadmindetall.setImageURI(imageUri)
        //--------------------------------------------------------------------------------------------

        administradoresLiveData = database.administradores().get(idAdmin)

        administradoresLiveData.observe(this, Observer {
            administradores = it
            nombreadmdetall.text = administradores.nombre_admin
           permisoadmindetall.text = administradores.permisos_admin
           emailadmindetall.text = administradores.email_admin
           contraseadmindetall.text = administradores.contraseña_admin
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.editar_item -> {
                val intent = Intent(this, NuevoAdmin::class.java)
                intent.putExtra("administradores", administradores)
                startActivityForResult(intent, EDIT_ACTIVITY)
            }
            R.id.eliminar_item -> {
                administradoresLiveData.removeObservers(this)
                CoroutineScope(Dispatchers.IO).launch {
                    database.administradores().delete(administradores)
                    ImagenController.deleteImage(
                        this@AdminActivity,
                        administradores.idAdmin.toLong()
                    )
                    this@AdminActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            resultCode == EDIT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                fotoadmindetall.setImageURI(data!!.data)
            }
        }
    }
}