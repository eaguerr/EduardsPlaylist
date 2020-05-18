package com.example.eduard.songs.ui.favorite_screen

import com.example.eduard.songs.model.Song
import com.example.eduard.songs.ui.base.BasePresenter
import com.example.eduard.songs.ui.base.BaseView


interface FavoriteScreenContract {

  interface View : BaseView<Presenter> {
    fun showCart(items: List<Song>, notify: Boolean)
  }

  interface Presenter : BasePresenter {
    fun loadCart(notify: Boolean)
    fun removeItem(item: Song)
  }
}