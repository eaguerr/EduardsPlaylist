package com.example.eduard.songs.ui.play_song_screen

import com.example.eduard.songs.model.FavoriteScreen
import com.example.eduard.songs.model.Song
import com.example.eduard.songs.model.SongsRepository
import com.example.eduard.songs.ui.play_song_screen.PlaySongContract.Presenter


class PlaySongPresenter(private val repository: SongsRepository, private val favoriteScreen: FavoriteScreen) : Presenter {

    private var song: Song? = null

    override fun start() {
    }

    override fun getSong(songId: Int): Song? {
        song?.let {
            return it
        }
        song = repository.getSongById(songId)
        return song
    }

    override fun removeSong(item: Song) {
        favoriteScreen.removeItem(item)
    }

    override fun addSong(item: Song) {
        favoriteScreen.addItem(item)
    }
}