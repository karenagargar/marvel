package edu.iesam.marvelapp.features.superheroes.data.local.xml

import android.content.Context
import android.util.Log
import edu.iesam.marvelapp.MainActivity
import edu.iesam.marvelapp.R
import edu.iesam.marvelapp.core.serialization.JsonSerializer
import edu.iesam.marvelapp.core.cache.CacheTTL
import edu.iesam.marvelapp.features.superheroes.domain.SuperHero

/**
 * Módulo de acceso a datos-> Conector (entre Repositorio y la Fuente de datos)
 * Conector para gestionar datos en un fichero XML a través de SharedPreferences
 * EL contexto nos permite acceder a la librería de sharedpreferences,
 * con el nombre y el modo de acceso como params
 */
/*
class SuperHeroXmlLocalDataSource(private val context: Context, private val serialization: AppSerialization) {
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.local_source_superhero), Context.MODE_PRIVATE)

    private val gson : Gson = Gson()

    public fun findAll(): List<SuperHero>{
        val superhero = SuperHero("1","SuperWoman","super-woman","super-woman.es")

        val json=serialization.toJson(superhero)
        serialization.fromJson(json, SuperHero::class.java)

        return emptyList()
    }
}*/
class SuperHeroXmlLocalDataSource(
    private val context: Context,
    private val serializer: JsonSerializer<List<SuperHero>>
) {
    companion object {

        private val keySuperHero: String = "superhero"
        private val ttl: Long =5*60*1000
    }
   val cache = CacheTTL(context)
    //Creo la fuente de datos xml: sharedPreferences
    val sharedPref = context.getSharedPreferences(
        keySuperHero,
        Context.MODE_PRIVATE
    )

    //Método para guardar un listado de SuperHero
    fun save(superHeroes: List<SuperHero>) {
        //Este apply{} es un Scope Function(apply,run,let) aplica el sharedPref.edit() -> devuelve el objeto para trabjar con el en cada linea
        sharedPref.edit().apply {
            putString(keySuperHero, serializer.toJson(superHeroes))
            apply()
        }
        cache.saveTime(keySuperHero)
    }

    public fun findAll(): List<SuperHero> {
        val cacheIsExpired= cache.isExpired(keySuperHero,ttl)
        if (!cacheIsExpired){
            Log.d(MainActivity::class.java.simpleName,cacheIsExpired.toString() )
            val jsonList: String? = sharedPref.getString(keySuperHero,null) //coge p0 y si no existe el valor por defecto, p1
            jsonList?.let { //sustituimos así ifnull para que solo lo haga si es algo, no nulo, y podemos acceder a ello como it
                return serializer.fromJson(it)
            }
        }
        return emptyList()
    }

    public fun findById(id: String): SuperHero?{
        return findAll().find { it.id == id }
    }

    public fun deleteById(id: String): Boolean{
        //save(findAll().filterNot{it.id == id}) -> mejor opción
        findById(id).let {
            val copySuperHeroes = findAll().toMutableList()
            copySuperHeroes.remove(it)
            save(copySuperHeroes)
            return true
        }
    }

    public fun deleteAll(){
//      save(emptyList<SuperHero>()) -> a mí se me ocurrió así, que no está mal pero es mejor opción el nativo
        sharedPref.edit().apply{
            remove(keySuperHero)
            apply()
        }
    }

}