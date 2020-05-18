package com.example.eduard.songs.ui.genre

import com.example.eduard.songs.model.SongsRepository


class SongsGenrePresenter(private val repository: SongsRepository, private val view: SongsGenreContract.View)
    : SongsGenreContract.Presenter {

    override fun start() {
        loadCategories()
    }

    private fun loadCategories() {
        view.showSongGenres(repository.getGenres())
    }
}