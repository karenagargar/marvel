package edu.iesam.marvelapp.features.superheroes.domain

interface SuperHeroRepository {
    fun getAll(): List<SuperHero>
}