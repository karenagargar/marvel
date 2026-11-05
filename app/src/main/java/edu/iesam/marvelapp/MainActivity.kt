package edu.iesam.marvelapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.iesam.marvelapp.features.superheroes.data.local.xml.SuperHeroXmlLocalDataSource
import edu.iesam.marvelapp.features.superheroes.domain.SuperHero

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Crear un fichero SahredPreferences como ejemplo, práctica no recomendable desde aquí
        val xml = SuperHeroXmlLocalDataSource(this)
        xml.save(
            mutableListOf(
                SuperHero("1", "superman1", "slug-de-prueba1", "url1"),
                SuperHero("2", "superman2", "slug-de-prueba2", "url2"),
                SuperHero("3", "superman3", "slug-de-prueba1", "url1"),
                SuperHero("4", "superman4", "slug-de-prueba2", "url2")
            )
        )

        val superhero = xml.findById("2")
        Log.d(MainActivity::class.java.simpleName, superhero.toString())
    }

}