package com.example.eduard.songs.model

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.eduard.songs.app.PlaylistApplication
import com.example.eduard.songs.model.events.DeleteSongFromFavoriteScreenEvent
import com.example.eduard.songs.model.events.FavoriteScreenEvent
import org.greenrobot.eventbus.EventBus


object FavoriteScreen {

  private val FAVORITES_KEY = "FAVORITES_KEY"
  private val gson = Gson()

  private var itemIds: MutableList<Int>? = null

  fun isInCart(item: Song): Boolean {
    return getCartItemIds()?.contains(item.id) == true
  }

  fun addItem(item: Song) {
    val itemIds = getCartItemIds()
    itemIds?.let {
      item.isMarkedFavorite = true
      itemIds.add(item.id)
      saveCart(FAVORITES_KEY, itemIds)
    }
  }

  fun removeItem(item: Song) {
    val itemIds = getCartItemIds()
    itemIds?.let {
      item.isMarkedFavorite = false
      val position = itemIds.indexOf(item.id)
      itemIds.remove(item.id)
      saveCart(FAVORITES_KEY, itemIds)
      EventBus.getDefault().post(DeleteSongFromFavoriteScreenEvent(position))
    }
  }

  private fun saveCart(key: String, list: List<Int>) {
    val json = gson.toJson(list)
    sharedPrefs().edit().putString(key, json).apply()
    EventBus.getDefault().post(FavoriteScreenEvent())
  }

  private fun getCartItemIds(): MutableList<Int>? {
    if (itemIds == null) {
      val json = sharedPrefs().getString(FAVORITES_KEY, "")
      val type = object : TypeToken<MutableList<Int>>() {}.type
      itemIds = gson.fromJson<MutableList<Int>>(json, type) ?: return mutableListOf()
    }
    return itemIds
  }

  fun getCartItems(): List<Song> {
    val itemIds = getCartItemIds()
    val items = mutableListOf<Song>()
    itemIds?.let {
      itemIds.forEach {
        val item = SongsRepository.getFoodById(it)
        item?.let {
          items.add(item)
        }
      }
    }
    return items
  }

  fun favoritesListSize(): Int {
    val itemIds = getCartItemIds()
    itemIds?.let {
      return itemIds.size
    }
    return 0
  }

  fun addAllSongsToFavorite() {
    val itemIds = getCartItemIds()
    itemIds?.let {
      val foods = SongsRepository.getFoods()
      foods.forEach { food ->
        if (!food.isMarkedFavorite) {
          addItem(food)
        }
      }
    }
  }

  fun clearFavoritesScreen() {
    val itemIds = getCartItemIds()
    itemIds?.let {
      itemIds.clear()
      val foods = SongsRepository.getFoods()
      foods.forEach { it.isMarkedFavorite = false }
      saveCart(FAVORITES_KEY, itemIds)
    }
  }

  private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(PlaylistApplication.getAppContext())
}