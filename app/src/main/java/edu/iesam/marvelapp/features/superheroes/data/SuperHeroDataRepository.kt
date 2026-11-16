package edu.iesam.marvelapp.features.superheroes.data

import edu.iesam.marvelapp.features.superheroes.data.local.xml.SuperHeroXmlLocalDataSource
import edu.iesam.marvelapp.features.superheroes.data.remote.mock.SuperHeroMockRemoteDataSource
import edu.iesam.marvelapp.features.superheroes.domain.SuperHero
import edu.iesam.marvelapp.features.superheroes.domain.SuperHeroRepository

/*
Clase encargada de gestionar la fuente de datos: xml, bbdd, Api, etc
*/

class SuperHeroDataRepository(private val xmlLocalDataSource: SuperHeroXmlLocalDataSource, private val mockRemoteDataSource: SuperHeroMockRemoteDataSource) : SuperHeroRepository {
    override fun getAll(): List<SuperHero> {
        return xmlLocalDataSource.findAll()
    }

    override fun update(superHero: SuperHero) {
        xmlLocalDataSource.save(mockRemoteDataSource.obtainAll())
        xmlLocalDataSource.update(superHero)
    }

}