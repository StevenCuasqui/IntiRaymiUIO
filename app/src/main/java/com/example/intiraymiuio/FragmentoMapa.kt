package com.example.intiraymiuio


import android.R
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_fragmento_mapa.*


class FragmentoMapa :SupportMapFragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap



  /* override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        /*val mapFragment =
            fragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?*/
        val mapFragment = getActivity()?.supportFragmentManager?.findFragmentById(R.id.mapa) as SupportMapFragment?
        val mapaFragmento = (activity as AppCompatActivity).supportFragmentManager?.findFragmentById(R.id.mapa) as SupportMapFragment?
        Log.i("Check","Hola")
        mapaFragmento?.getMapAsync(this)
    }*/

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        
        Log.i("Check","Hola1")
        //val mapFragment = getActivity()?.supportFragmentManager?.findFragmentById(mapa.id) as SupportMapFragment?
        //val mapaFragmento = (activity as AppCompatActivity).supportFragmentManager?.findFragmentById(mapa.id) as SupportMapFragment?
        //Log.i("Check2",mapFragment.toString())
        getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        Log.i("Check","Hola")
        mMap = map
        val quito = LatLng(-0.225219, -78.5248)
        val nivelZoom = 17F
        mMap.addMarker(MarkerOptions().position(quito).title("Marker in Quito"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quito, nivelZoom))
    }
    fun dibujarRuta(){

    }

    companion object{
        fun newInstance(): FragmentoMapa = FragmentoMapa()

    }
}
