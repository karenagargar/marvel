package edu.iesam.marvelapp.core.cache

import android.content.Context
import edu.iesam.marvelapp.R

//Chema ha hecho una interfaz, revisar por qué
interface AppCache{
    fun saveTime(key: String)
    fun isExpired(key: String, millis: Long): Boolean
}
class CacheTTL(private val context: Context): AppCache {
    //Creo donde voy a almacenar mis tiempos de cache
    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.cache_source_name),
        Context.MODE_PRIVATE
    )

    override fun saveTime (key: String){
        sharedPref.edit().apply {
            putLong(key, System.currentTimeMillis())
            apply()
        }
    }
    override fun isExpired(key: String, millis: Long): Boolean{
        val savedTime: Long = sharedPref.getLong(key,-1) //0? -1?
        val nowTime =System.currentTimeMillis()
        return if (nowTime-savedTime<=millis) false
        else true
    }

}