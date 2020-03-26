package com.example.intiraymiuio

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import android.provider.Settings
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*


class FragmentoMapa :SupportMapFragment(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener {
    private lateinit var mMap: GoogleMap
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var database: DatabaseReference
    var coordenadas = LatLng(0.0,0.0)

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)

        //val mapFragment = getActivity()?.supportFragmentManager?.findFragmentById(mapa.id) as SupportMapFragment?
        //val mapaFragmento = (activity as AppCompatActivity).supportFragmentManager?.findFragmentById(mapa.id) as SupportMapFragment?
        //Log.i("Check2",mapFragment.toString())
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context as Activity)
        //this.coordenadas=obtenerUltimaLocalizacion()
        database = FirebaseDatabase.getInstance().reference
        Log.i("Check2",obtenerUltimaLocalizacion().toString())
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
        fun newInstance(): FragmentoMapa = FragmentoMapa()
        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }
    }

    override fun onPolylineClick(p0: Polyline?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun revisarPermisos(): Boolean {
        if (ActivityCompat.checkSelfPermission(context as Activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun pedirPermisos() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
            }
        }
    }

    private fun localizacionHabilitada(): Boolean {
        var locationManager: LocationManager = getActivity()?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUltimaLocalizacion():LatLng {
        var coord:LatLng = LatLng(0.0,0.0)
        if (revisarPermisos()) {

            if (localizacionHabilitada()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(context as Activity) { task ->
                    var location: Location? = task.result

                    if (location == null) {
                        pedirLocalizacionNuevo()
                    } else {
                        coord=LatLng(location.latitude,location.longitude)
                        Log.i("Coordenadas en Funcion",coord.toString())
                        subirLocalizacionFirebase("Capitan",coord)
                        pedirLocalizacionNuevo()

                    }


                }

            } else {
                Toast.makeText(context as Activity, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            pedirPermisos()
        }
        Log.i("Coordenadas en Final",coord.toString())
        return coord
    }

    @SuppressLint("MissingPermission")
    private fun pedirLocalizacionNuevo() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 30000
        mLocationRequest.fastestInterval = 10000
        //mLocationRequest.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context as Activity)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            var coordi=LatLng(mLastLocation.latitude,mLastLocation.longitude)
            Log.i("Actual","Cambios")
            Log.i("Ultima Lat",mLastLocation.latitude.toString())
            Log.i("Ultima Lng",mLastLocation.longitude.toString())
            subirLocalizacionFirebase("Capitan",coordi)
            obtenerLocalizacionFirebase(database)
        }
    }

    private fun subirLocalizacionFirebase(idUsuario: String, latitudLongitud:LatLng){
        database.child("usuarios").child(idUsuario).setValue(latitudLongitud)
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
                Log.i("Coordenadas Recuperadas",coordFirebase.toString())
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