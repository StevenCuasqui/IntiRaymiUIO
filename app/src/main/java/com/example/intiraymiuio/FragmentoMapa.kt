package com.example.intiraymiuio

import com.example.intiraymiuio.MainActivity
import android.R
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ContentView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_fragmento_mapa.*


class FragmentoMapa :SupportMapFragment(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener {
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

        mMap = map
        val quito = LatLng(-0.208478,-78.495465)
        val nivelZoom = 17F
        mMap.addMarker(MarkerOptions().position(quito).title("Marker in Quito"))


        val poliinea = mMap.addPolyline(PolylineOptions().clickable(true).add(
            LatLng(-0.208478,-78.495465),
            LatLng(-0.210059, -78.493623),
            LatLng(-0.208106, -78.490319),
            LatLng(-0.207398, -78.489058),
            LatLng(-0.208127, -78.488452),
            LatLng(-0.208175, -78.488401),
            LatLng(-0.208593, -78.489017),
            LatLng(-0.209323, -78.488133),
            LatLng(-0.209398, -78.488085),
            LatLng(-0.209508, -78.488128),
            LatLng(-0.209694, -78.488421),
            LatLng(-0.210155, -78.489588),
            LatLng(-0.210391, -78.489905),
            LatLng(-0.210694, -78.490180),
            LatLng(-0.211547, -78.489157)
        ))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quito, nivelZoom))
        mMap.setOnPolylineClickListener(this)
    }

    companion object{
        fun newInstance(): FragmentoMapa = FragmentoMapa()

    }

    override fun onPolylineClick(p0: Polyline?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
