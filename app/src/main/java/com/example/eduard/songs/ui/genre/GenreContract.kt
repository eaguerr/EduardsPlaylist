package com.example.eduard.songs.ui.genre

import com.example.eduard.songs.model.Song
import com.example.eduard.songs.ui.base.BasePresenter
import com.example.eduard.songs.ui.base.BaseView


interface GenreContract {

    interface View : BaseView<Presenter> {
        fun showSongs(items: List<Song>)
    }

    interface Presenter : BasePresenter {
        fun loadGenre(category: String)
    }
}