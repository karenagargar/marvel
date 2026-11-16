package edu.iesam.marvelapp.features.superheroes.domain

class GetSuperheroUseCase (private val superHeroRepository: SuperHeroRepository) {
    operator fun invoke(id:String): SuperHero?{
        return superHeroRepository.findById(id)
    }
}