package com.example.intiraymiuio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var etCorreo:EditText? =null
    private var etContrasenia:EditText? = null
    private var correo: String? = null
    private var contrasenia: String? = null

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialise()

        botonAccesoCapitan.setOnClickListener{
            loginCapitan()
        }

        botonAccesoEspectador.setOnClickListener {
            val intentEspectador = Intent(this,MainActivity::class.java)
            startActivity(intentEspectador)
        }

    }

    private fun initialise() {
        etCorreo = findViewById<View>(R.id.nombreText) as EditText
        etContrasenia =findViewById<View>(R.id.contraseniaText) as EditText

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("usuarios").child("Acceso")
        mAuth = FirebaseAuth.getInstance()
    }

    private fun loginCapitan(){
        correo = etCorreo?.text.toString()
        contrasenia = etContrasenia?.text.toString()
        if (!TextUtils.isEmpty(correo) && !TextUtils.isEmpty(contrasenia)) {

            mAuth!!.signInWithEmailAndPassword(correo!!, contrasenia!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        irActivityCapitan()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this@LoginActivity, "Fallo de Autenticacion",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show()
        }

    }

    private fun irActivityCapitan(){
        val intentCapitan = Intent(this,MainActivity::class.java)
        startActivity(intentCapitan)
    }
}
