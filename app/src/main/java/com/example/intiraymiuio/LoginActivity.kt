package com.example.intiraymiuio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var etCorreo:EditText? =null
    private var etContrasenia:EditText? = null
    private var correo: String? = null
    private var contrasenia: String? = null


    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialise()

        botonAccesoCapitan.setOnClickListener{
            loginCapitan()
        }

        botonAccesoEspectador.setOnClickListener {
            irActivityEspectador()
        }

    }

    private fun initialise() {
        etCorreo = findViewById<View>(R.id.nombreText) as EditText
        etContrasenia =findViewById<View>(R.id.contraseniaText) as EditText

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
        intentCapitan.putExtra("Capitan",true)
        startActivity(intentCapitan)
    }

    private fun irActivityEspectador(){
        val intentEspectador = Intent(this,MainActivity::class.java)
        intentEspectador.putExtra("Capitan",false)
        startActivity(intentEspectador)
    }
}
