package com.example.eduard.songs.ui.favorite_screen

import com.example.eduard.songs.model.FavoriteScreen
import com.example.eduard.songs.model.Song
import com.example.eduard.songs.ui.favorite_screen.FavoriteScreenContract.Presenter
import com.example.eduard.songs.ui.favorite_screen.FavoriteScreenContract.View


class FavoriteScreenPresenter(private val favoriteScreen: FavoriteScreen, private val favoriteScreenView: View) : Presenter {
    override fun start() {
        loadFavoriteScreen(true)
    }

    override fun loadFavoriteScreen(notify: Boolean) {
        favoriteScreenView.presentFavoriteScreen(favoriteScreen.getFavScreenSongs(), notify)
    }

    override fun removeItem(item: Song) {
        favoriteScreen.removeItem(item)
    }
}