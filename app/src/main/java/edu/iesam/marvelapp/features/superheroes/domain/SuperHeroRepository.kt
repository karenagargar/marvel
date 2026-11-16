package edu.iesam.marvelapp.features.superheroes.domain

interface SuperHeroRepository {
    fun getAll(): List<SuperHero>
    fun findById(id: String): edu.iesam.marvelapp.features.superheroes.domain.SuperHero?
}