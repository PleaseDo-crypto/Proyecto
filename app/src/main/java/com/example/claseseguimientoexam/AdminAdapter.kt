package com.example.claseseguimientoexam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_admin.view.*

class AdminAdapter(private val mContext: Context,private val ListaAdmin: List<Administradores>) :
    ArrayAdapter<Administradores>(mContext,0,ListaAdmin){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_admin, parent, false)
        val administradores = ListaAdmin[position]

        layout.nombreadm_list.text = administradores.nombre_admin
        layout.permisoadm_list.text = administradores.permisos_admin
        layout.emailadm_list.text = administradores.email_admin
        layout.contraadm_list.text = administradores.contraseña_admin

        val imageUri = ImagenController.getImageUri(mContext, administradores.idAdmin.toLong())
        layout.listfoto_admin.setImageURI(imageUri)

        return layout
        }
    }