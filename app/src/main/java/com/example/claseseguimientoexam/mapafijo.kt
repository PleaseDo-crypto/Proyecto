package com.example.claseseguimientoexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class mapafijo : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map:GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapsfixedpont)

        createFragment()
    }
    private fun createFragment(){
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap){
        map=googleMap
        createMaker()


    }
    private fun createMaker(){
        val coordenada = LatLng(19.3971604,-99.4845182)
        val marcador = MarkerOptions().position(coordenada).title("Casa Javier")
        map.addMarker(marcador)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenada,18f),4000,null
        )
    }

}