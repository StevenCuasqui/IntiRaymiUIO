package com.example.intiraymiuio


import android.os.Bundle
import android.content.Context
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity


class FragmentoMapa :SupportMapFragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap


   override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        /*val mapFragment =
            fragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?*/
        val mapFragment = getActivity()?.supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
        val mapaFragmento = (activity as AppCompatActivity).supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
        Log.i("Check","Hola")
        mapaFragmento?.getMapAsync(this)
    }

   /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

       var rootView = inflater.inflate(R.layout.activity_fragmento_mapa, container, false)
       Log.i("Check","Hola")

       /*val mapFragment =
           fragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?*/
       val mapFragment = getActivity()?.supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?
       val mapaFragmento = (activity as AppCompatActivity).supportFragmentManager?.findFragmentById(R.id.map) as SupportMapFragment?

       mapaFragmento?.getMapAsync(this)
       Log.i("Check","bye")
        return rootView
    }*/

    override fun onMapReady(map: GoogleMap) {
        mMap = map as GoogleMap;

        val quito = LatLng(-0.225219, -78.5248)
        mMap.addMarker(MarkerOptions().position(quito).title("Marker in Quito"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(quito))
    }

    companion object{
        fun newInstance(): FragmentoMapa = FragmentoMapa()
    }
}
