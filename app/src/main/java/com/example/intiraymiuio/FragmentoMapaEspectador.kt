package com.example.intiraymiuio


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*


class FragmentoMapaEspectador :SupportMapFragment(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener {
    private lateinit var mMap: GoogleMap
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var database: DatabaseReference
    var coordenadas = LatLng(0.0,0.0)

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
        database = FirebaseDatabase.getInstance().reference
        obtenerLocalizacionFirebase(database)
        getMapAsync(this)
    }


    override fun onMapReady(map: GoogleMap) {

        mMap = map
        val posicionInicial = LatLng(-0.208478,-78.495465)
        val nivelZoom = 17F
        val marcadorInicio = mMap.addMarker(MarkerOptions().position(posicionInicial).title("Inicio de Ruta"))
        marcadorInicio.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.salida_icon))
        mMap.addPolyline(PolylineOptions().clickable(true).add(
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
        val posicionFinal = LatLng(-0.211547, -78.489157)
        val marcadorFinal = mMap.addMarker(MarkerOptions().position(posicionFinal).title("Fin de Ruta"))
        marcadorFinal.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.llegada_icon))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicionInicial, nivelZoom))
        mMap.setOnPolylineClickListener(this)
    }

    companion object{
        fun newInstance(): FragmentoMapaEspectador = FragmentoMapaEspectador()
        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }
    }

    override fun onPolylineClick(p0: Polyline?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun obtenerLocalizacionFirebase(firebaseData: DatabaseReference):LatLng{
        var coordFirebase:LatLng = LatLng(0.0,0.0)

        val datos = firebaseData.child("usuarios").child("Capitan")
        datos.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                println(error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot!!.children
                var coorden = DoubleArray(2)
                var i = 0
                children.forEach {
                    coorden.set(i, it.getValue() as Double)
                    i++
                }
                coordFirebase = LatLng(coorden[0],coorden[1])
                Log.i("Coordenadas RecuE",coordFirebase.toString())
                navegarUbicacion(coordFirebase)
            }
        })
        return coordFirebase
    }
    private fun navegarUbicacion(coordenadas:LatLng){
        val nivelZoomCoordenadas = 17F
        val Marcador=mMap.addMarker(MarkerOptions().position(coordenadas).title("Capitán"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, nivelZoomCoordenadas))
    }
}
