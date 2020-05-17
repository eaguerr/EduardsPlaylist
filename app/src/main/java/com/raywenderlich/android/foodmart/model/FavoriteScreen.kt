package com.raywenderlich.android.foodmart.model

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.raywenderlich.android.foodmart.app.PlaylistApplication
import com.raywenderlich.android.foodmart.model.events.DeleteSongFromFavoriteScreenEvent
import com.raywenderlich.android.foodmart.model.events.FavoriteScreenEvent
import org.greenrobot.eventbus.EventBus


object FavoriteScreen {

  private val KEY_CART = "KEY_CART"
  private val gson = Gson()

  private var itemIds: MutableList<Int>? = null

  fun isInCart(item: Song): Boolean {
    return getCartItemIds()?.contains(item.id) == true
  }

  fun addItem(item: Song) {
    val itemIds = getCartItemIds()
    itemIds?.let {
      item.isInCart = true
      itemIds.add(item.id)
      saveCart(KEY_CART, itemIds)
    }
  }

  fun removeItem(item: Song) {
    val itemIds = getCartItemIds()
    itemIds?.let {
      item.isInCart = false
      val position = itemIds.indexOf(item.id)
      itemIds.remove(item.id)
      saveCart(KEY_CART, itemIds)
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
      val json = sharedPrefs().getString(KEY_CART, "")
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

  fun cartSize(): Int {
    val itemIds = getCartItemIds()
    itemIds?.let {
      return itemIds.size
    }
    return 0
  }

  fun addAllToCart() {
    val itemIds = getCartItemIds()
    itemIds?.let {
      val foods = SongsRepository.getFoods()
      foods.forEach { food ->
        if (!food.isInCart) {
          addItem(food)
        }
      }
    }
  }

  fun clearCart() {
    val itemIds = getCartItemIds()
    itemIds?.let {
      itemIds.clear()
      val foods = SongsRepository.getFoods()
      foods.forEach { it.isInCart = false }
      saveCart(KEY_CART, itemIds)
    }
  }

  private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(PlaylistApplication.getAppContext())
}