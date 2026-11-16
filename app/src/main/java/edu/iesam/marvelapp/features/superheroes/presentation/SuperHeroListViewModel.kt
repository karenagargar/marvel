package edu.iesam.marvelapp.features.superheroes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.iesam.marvelapp.features.superheroes.domain.GetSuperheroesUseCase
import edu.iesam.marvelapp.features.superheroes.domain.SuperHero

//Pasamos por constructor e caso de uso porque así se puede testear más adelante, si lo hacemos dentro no podríamos
class SuperHeroListViewModel(private val getSuperheroesUseCase: GetSuperheroesUseCase) : ViewModel() {
    //hacemos una variable que no sea mutable
    private val _uiState= MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState
    fun loadSuperheroes(){
//        1.Mostramos el loading.
        _uiState.value= UiState(isLoading = true)

//        2.Mostramos Datos
        val superheroes = getSuperheroesUseCase()
        _uiState.value= UiState(superheroes=superheroes)
    }
    fun loadSuperhero(){}
    data class UiState(val isLoading: Boolean=false, val hasError: Boolean=false, val superheroes: List<SuperHero>? = null, val superHero: SuperHero?=null)

    companion object{
        fun factory(getSuperheroesUseCase: GetSuperheroesUseCase) = viewModelFactory {
            initializer { SuperHeroListViewModel(getSuperheroesUseCase) }
        }
    }
}