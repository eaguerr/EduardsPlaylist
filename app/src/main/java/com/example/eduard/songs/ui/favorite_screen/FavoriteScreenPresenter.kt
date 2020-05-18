package com.example.eduard.songs.ui.favorite_screen

import com.example.eduard.songs.model.FavoriteScreen
import com.example.eduard.songs.model.Song


class FavoriteScreenPresenter(private val cart: FavoriteScreen, private val favoriteScreenView: FavoriteScreenContract.View) : FavoriteScreenContract.Presenter {
  override fun start() {
    loadCart(true)
  }

  override fun loadCart(notify: Boolean) {
    favoriteScreenView.showCart(cart.getCartItems(), notify)
  }

  override fun removeItem(item: Song) {
    cart.removeItem(item)
  }
}