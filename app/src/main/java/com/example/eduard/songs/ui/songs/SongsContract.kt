package com.example.eduard.songs.ui.songs

import com.example.eduard.songs.model.Song
import com.example.eduard.songs.ui.base.BasePresenter
import com.example.eduard.songs.ui.base.BaseView


interface SongsContract {

    interface View : BaseView<Presenter> {
        fun showItems(items: List<Song>)
    }

    interface Presenter : BasePresenter {
        fun favoritesListSize(): Int
        fun addAllSongsToFavorite()
        fun clearAllSongsFromFavorite()
        fun removeItem(item: Song)
        fun addItem(item: Song)
    }
}