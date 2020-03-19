package com.example.intiraymiuio



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toolbar : ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){

            R.id.navigation_songs -> {
                toolbar.title ="Mapa"
                val songsFragment = FragmentoMapa.newInstance()
                openFragment(songsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_albums -> {
                toolbar.title ="Agenda"
                val agendaFragmento = FragmentoAgenda.newInstance()
                openFragment(agendaFragmento)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_artists -> {
                toolbar.title ="Info"
                val infoFragmento = FragmentoInfo.newInstance()
                openFragment(infoFragmento)
                return@OnNavigationItemSelectedListener true
            }


        }
        false
    }

    private fun openFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }
}
