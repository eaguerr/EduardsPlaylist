package com.raywenderlich.android.foodmart.model

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raywenderlich.android.foodmart.app.PlaylistApplication


object Favorites {

  private val KEY_FAVORITES = "KEY_FAVORITES"
  private val gson = Gson()

  private var favorites: MutableList<Int>? = null

  fun isFavorite(song: Song): Boolean = getFavorites()?.contains(song.id) == true

  fun addFavorite(song: Song) {
    val favorites = getFavorites()
    favorites?.let {
      song.isFavorite = true
      favorites.add(song.id)
      saveFavorites(KEY_FAVORITES, favorites)
    }
  }

  fun removeFavorite(song: Song) {
    val favorites = getFavorites()
    favorites?.let {
      song.isFavorite = false
      favorites.remove(song.id)
      saveFavorites(KEY_FAVORITES, favorites)
    }
  }

  fun getFavorites(): MutableList<Int>? {
    if (favorites == null) {
      val json = sharedPrefs().getString(KEY_FAVORITES, "")
      val type = object : TypeToken<MutableList<Int>>() {}.type
      favorites = gson.fromJson<MutableList<Int>>(json, type) ?: return mutableListOf()
    }
    return favorites
  }

  fun saveFavorites(list: List<Int>) {
    saveFavorites(KEY_FAVORITES, list)
  }

  private fun saveFavorites(key: String, list: List<Int>) {
    val json = gson.toJson(list)
    sharedPrefs().edit().putString(key, json).apply()
  }

  private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(PlaylistApplication.getAppContext())
}