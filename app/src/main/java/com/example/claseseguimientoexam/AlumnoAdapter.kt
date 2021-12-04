package com.example.claseseguimientoexam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_alumno.view.*

class AlumnoAdapter(private val mContext:Context,private val listaAlumnos: List<Alumno>):
ArrayAdapter<Alumno>(mContext,0,listaAlumnos){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout= LayoutInflater.from(mContext).inflate(R.layout.item_alumno,parent,false)
        val alumno = listaAlumnos[position]

        layout.matriculalis.text="No.${alumno.matricula.toString()}"
        layout.nombrelis.text=alumno.nombre
        layout.emaillis.text=alumno.email
        layout.passlis.text=alumno.pass

        val imageUri=ImagenController.getImageUri(mContext,alumno.idAlumno.toLong())
        layout.fotolist.setImageURI(imageUri)

        return layout
    }
}