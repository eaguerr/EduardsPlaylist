package com.example.eduard.songs.ui

import com.example.eduard.songs.model.FavoriteScreen
import com.example.eduard.songs.model.SongsRepository
import com.example.eduard.songs.ui.cart.FavoriteScreenContract
import com.example.eduard.songs.ui.cart.FavoriteScreenPresenter
import com.example.eduard.songs.ui.categories.SongsGenreContract
import com.example.eduard.songs.ui.categories.SongsGenrePresenter
import com.example.eduard.songs.ui.categories.GenreContract
import com.example.eduard.songs.ui.categories.GenrePresenter
import com.example.eduard.songs.ui.play_song_screen.PlaySongContract
import com.example.eduard.songs.ui.play_song_screen.PlaySongPresenter
import com.example.eduard.songs.ui.songs.SongsContract
import com.example.eduard.songs.ui.songs.SongsPresenter


object Injection {

  fun provideFoodRepository(): SongsRepository = SongsRepository

  fun provideCart(): FavoriteScreen = FavoriteScreen

  fun provideItemsPresenter(songsView: SongsContract.View): SongsContract.Presenter =
      SongsPresenter(provideFoodRepository(), provideCart(), songsView)

    fun provideFoodPresenter(foodView: PlaySongContract.View): PlaySongContract.Presenter =
            PlaySongPresenter(provideFoodRepository(), provideCart(), foodView)

  fun provideCartPresenter(favoriteScreenView: FavoriteScreenContract.View): FavoriteScreenContract.Presenter =
      FavoriteScreenPresenter(provideCart(), favoriteScreenView)

  fun provideCategoriesPresenter(view: SongsGenreContract.View): SongsGenreContract.Presenter =
      SongsGenrePresenter(provideFoodRepository(), view)

  fun provideCategoryPresenter(itemsView: GenreContract.View): GenreContract.Presenter =
      GenrePresenter(provideFoodRepository(), itemsView)
}