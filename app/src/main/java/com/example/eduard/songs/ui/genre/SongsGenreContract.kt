package com.example.eduard.songs.ui.genre

import com.example.eduard.songs.ui.base.BasePresenter
import com.example.eduard.songs.ui.base.BaseView


interface SongsGenreContract {

  interface View : BaseView<Presenter> {
    fun showCategories(categories: List<String>)
  }

  interface Presenter : BasePresenter
}