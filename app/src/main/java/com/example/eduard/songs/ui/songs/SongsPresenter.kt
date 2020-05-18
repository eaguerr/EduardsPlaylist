package com.example.eduard.songs.ui.songs

import com.example.eduard.songs.model.FavoriteScreen
import com.example.eduard.songs.model.Song
import com.example.eduard.songs.model.SongsRepository


class SongsPresenter(private val repository: SongsRepository, private val favoriteScreen: FavoriteScreen, private val songsView: SongsContract.View)
    : SongsContract.Presenter {

    override fun start() {
        loadSongs()
    }

    private fun loadSongs() {
        songsView.showItems(repository.getSongs())
    }

    override fun favoritesListSize() = favoriteScreen.favoritesListSize()

    override fun addAllSongsToFavorite() {
        favoriteScreen.addAllSongsToFavorite()
    }

    override fun clearAllSongsFromFavorite() {
        favoriteScreen.clearFavoritesScreen()
    }

    override fun removeItem(item: Song) {
        favoriteScreen.removeItem(item)
    }

    override fun addItem(item: Song) {
        favoriteScreen.addItem(item)
    }
}