package edu.iesam.marvelapp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import edu.iesam.marvelapp.core.serialization.GsonJsonSerializer
import edu.iesam.marvelapp.core.serialization.KotlinJsonSerializer
import edu.iesam.marvelapp.features.superheroes.data.SuperHeroDataRepository
import edu.iesam.marvelapp.features.superheroes.data.local.xml.SuperHeroXmlLocalDataSource
import edu.iesam.marvelapp.features.superheroes.domain.GetSuperheroesUseCase
import edu.iesam.marvelapp.features.superheroes.domain.SuperHero
import edu.iesam.marvelapp.features.superheroes.presentation.SuperHeroListViewModel

class MainActivity : AppCompatActivity() {

    //    private val viewModel : SuperHeroListViewModel by viewModels () //Cuando tenemos parametros por constructor no podemos hacer byviewmodels
    val useCase = GetSuperheroesUseCase(
        SuperHeroDataRepository(
            SuperHeroXmlLocalDataSource(
                this,
                GsonJsonSerializer.create()
            )
        )
    )

    private val viewModel: SuperHeroListViewModel by viewModels {
        SuperHeroListViewModel.factory(useCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("MainActivity", "OnCreate")
        //guardo los observadores
        initObserver()
        //ejecuto la carga que cambia el observador y ejecuta las cosas del bind
        viewModel.loadSuperheroes()
    }

    private fun initObserver() {
        viewModel.uiState.observe(this) { uiState ->
            bind(uiState)
        }
    }

    //    Se recoge la información del viewModel
    private fun bind(uiState: SuperHeroListViewModel.UiState) {
        if (uiState.isLoading) {
            //show loading
        } else {
            //hideloading
        }

        if (uiState.hasError) {
            //show error view
        } else {
            //hide error view
        }

        uiState.superheroes?.let { superHeroes ->
            if (superHeroes.isEmpty()) {
                //muestro pantalla de listado vacío
            } else {
                //muestro listado de superheroes
                // mostrar la info en un RV
                //por ahora vamos a enseñar el primero
                renderUi(superHeroes.first())
            }

        }

    }

    private fun renderUi(superHero: SuperHero) {

        val labelNameSuperHero =
            findViewById<ConstraintLayout>(R.id.superhero_layout).findViewById<TextView>(R.id.superhero_name)
        labelNameSuperHero.text = superHero.name
        val labelSlugSuperHero =
            findViewById<ConstraintLayout>(R.id.superhero_layout).findViewById<TextView>(R.id.superhero_slug)
        labelSlugSuperHero.text = superHero.slug
    }
}