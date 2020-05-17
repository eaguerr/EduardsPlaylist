package com.example.eduard.songs.ui.songs

import com.example.eduard.songs.model.FavoriteScreen
import com.example.eduard.songs.model.Song
import com.example.eduard.songs.model.SongsRepository


class SongsPresenter(private val repository: SongsRepository, private val cart: FavoriteScreen, private val songsView: SongsContract.View)
  : SongsContract.Presenter {

  override fun start() {
    loadFoods()
  }

  private fun loadFoods() {
    songsView.showItems(repository.getFoods())
  }

  override fun cartSize() = cart.cartSize()

  override fun addAllToCart() {
    cart.addAllToCart()
  }

  override fun clearCart() {
    cart.clearCart()
  }

  override fun removeItem(item: Song) {
    cart.removeItem(item)
  }

  override fun addItem(item: Song) {
    cart.addItem(item)
  }
}