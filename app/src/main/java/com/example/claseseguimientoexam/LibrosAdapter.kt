package com.example.claseseguimientoexam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_libros.view.*

class LibrosAdapter(private val mContext:Context, private val listaLibros: List<Libros>):
ArrayAdapter<Libros>(mContext,0,listaLibros){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout= LayoutInflater.from(mContext).inflate(R.layout.item_libros,parent,false)
        val libros = listaLibros[position]

        layout.nombrecar_list.text=libros.titulo
        layout.generolist.text=libros.genero
        layout.coordinador_list.text=libros.sinopsis
        layout.edificio_list.text=libros.autor

        val imagelibro=ImagenController.getImageUri(mContext,libros.idLibro.toLong())
        layout.foto_carrera.setImageURI(imagelibro)

        return layout
    }
}