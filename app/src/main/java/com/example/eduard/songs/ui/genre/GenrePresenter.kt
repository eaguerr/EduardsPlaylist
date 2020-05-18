package com.example.eduard.songs.ui.genre

import com.example.eduard.songs.model.SongsRepository


class GenrePresenter(private val repository: SongsRepository, private val itemsView: GenreContract.View)
  : GenreContract.Presenter {

  override fun start() {
  }

  override fun loadCategory(category: String) {
    itemsView.showItems(repository.getFoodsForCategory(category))
  }
}