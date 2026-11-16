package edu.iesam.marvelapp.features.superheroes.data.remote.mock

import edu.iesam.marvelapp.features.superheroes.domain.SuperHero

class SuperHeroMockRemoteDataSource {
    fun obtainAll(): List<SuperHero>{
        return listOf(
            SuperHero("1","Nombre1","Slug1","https://1"),
            SuperHero("2","Nombre2","Slug2","https://2"),
            SuperHero("3","Nombre3","Slug3","https://3"),
            SuperHero("4","Nombre4","Slug4","https://4")
        )
    }
}