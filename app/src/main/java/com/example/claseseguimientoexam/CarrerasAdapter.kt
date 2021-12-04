package com.example.claseseguimientoexam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_alumno.view.*
import kotlinx.android.synthetic.main.item_carrera.view.*

class CarrerasAdapter(private val mContext:Context, private val listaCarreras:List<Carreras>):
ArrayAdapter<Carreras>(mContext,0,listaCarreras){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout= LayoutInflater.from(mContext).inflate(R.layout.item_carrera,parent,false)
        val carreras = listaCarreras[position]

        layout.nombrecar_list.text=carreras.nombrecarr
        layout.cuatri_list.text=carreras.cutrimestre
        layout.edificio_list.text=carreras.edificio
        layout.coordinador_list.text=carreras.coordinador

        val imageUri=ImagenController.getImageUri(mContext,carreras.idCarrera.toLong())
        layout.foto_carrera.setImageURI(imageUri)

        return layout
    }
}