package com.example.claseseguimientoexam

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nuevo_admin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoAdmin : AppCompatActivity() {
    private val SELECT_ACTIVITY = 49
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_admin)

        var idAdmin: Int? = null

        if (intent.hasExtra("administradores")) {
            val administradores = intent.extras?.getSerializable("administradores") as Administradores
            nombreadmin.setText(administradores.nombre_admin)
            permisoadmin.setText(administradores.permisos_admin)
            emailadmin.setText(administradores.email_admin)
            contraseñaadmin.setText(administradores.contraseña_admin)
            idAdmin = administradores.idAdmin
            //-----------
            val imageUri = ImagenController.getImageUri(this, idAdmin.toLong())
            fotoadmin.setImageURI(imageUri)

        }

        val database = AdminDatabase.getDatabase(this)
        registrar_admin.setOnClickListener{
            val nombre = nombreadmin.text.toString()
            val permisos = permisoadmin.text.toString()
            val email = emailadmin.text.toString()
            val pass = contraseñaadmin.text.toString()

            val administradores = Administradores(R.drawable.ic_launcher_background,nombre, permisos,email,pass)
            if(idAdmin != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    administradores.idAdmin = idAdmin
                    database.administradores().update(administradores)
                    imageUri?.let {
                        val intent = Intent()
                        intent.data = it
                        setResult(Activity.RESULT_OK, intent)
                        ImagenController.saveImage(this@NuevoAdmin, idAdmin.toLong(), it)
                    }
                    this@NuevoAdmin.finish()

                }
            }else{

                CoroutineScope(Dispatchers.IO).launch {

                    val id = database.administradores().insertAll(administradores)[0]
                    imageUri?.let {
                        ImagenController.saveImage(this@NuevoAdmin, id, it)
                    }
                    this@NuevoAdmin.finish()
                }
            }

        }

        fotoadmin.setOnClickListener {
            ImagenController.selectPhotoFromGallery(this, SELECT_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data
                fotoadmin.setImageURI(imageUri)
            }
        }
    }
}