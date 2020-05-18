package com.example.eduard.songs.ui.genre

import com.example.eduard.songs.model.SongsRepository
import com.example.eduard.songs.ui.genre.GenreContract.Presenter
import com.example.eduard.songs.ui.genre.GenreContract.View


class GenrePresenter(private val repository: SongsRepository, private val itemsView: View) : Presenter {

    override fun start() {
    }

    override fun loadGenre(genre: String) {
        itemsView.showSongs(repository.getSongsForGenre(genre))
    }
}