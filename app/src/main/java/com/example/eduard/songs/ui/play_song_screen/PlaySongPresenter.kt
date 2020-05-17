package com.example.eduard.songs.ui.play_song_screen

import com.example.eduard.songs.model.FavoriteScreen
import com.example.eduard.songs.model.Song
import com.example.eduard.songs.model.SongsRepository


class PlaySongPresenter(private val repository: SongsRepository, private val cart: FavoriteScreen, private val foodView: PlaySongContract.View) : PlaySongContract.Presenter {

  private var song: Song? = null

  override fun start() {
  }

  override fun getFood(foodId: Int): Song? {
    song?.let {
      return it
    }
    song = repository.getFoodById(foodId)
    return song
  }

  override fun removeItem(item: Song) {
    cart.removeItem(item)
  }

  override fun addItem(item: Song) {
    cart.addItem(item)
  }
}