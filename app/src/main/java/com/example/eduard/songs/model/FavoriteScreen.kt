package com.example.eduard.songs.model

import android.preference.PreferenceManager
import com.example.eduard.songs.app.PlaylistApplication
import com.example.eduard.songs.model.events.DeleteSongFromFavoriteScreenEvent
import com.example.eduard.songs.model.events.FavoriteScreenEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.greenrobot.eventbus.EventBus


object FavoriteScreen {

    private val FAVORITES_KEY = "FAVORITES_KEY"
    private val gson = Gson()

    private var itemIds: MutableList<Int>? = null

    fun isMarkedFavorite(item: Song): Boolean {
        return getFavoriteScreenSongIds()?.contains(item.id) == true
    }

    fun addItem(song: Song) {
        val songIds = getFavoriteScreenSongIds()
        songIds?.let {
            song.isMarkedFavorite = true
            songIds.add(song.id)
            saveFavScreen(FAVORITES_KEY, songIds)
        }
    }

    fun removeItem(item: Song) {
        val itemIds = getFavoriteScreenSongIds()
        itemIds?.let {
            item.isMarkedFavorite = false
            val position = itemIds.indexOf(item.id)
            itemIds.remove(item.id)
            saveFavScreen(FAVORITES_KEY, itemIds)
            EventBus.getDefault().post(DeleteSongFromFavoriteScreenEvent(position))
        }
    }

    private fun saveFavScreen(key: String, list: List<Int>) {
        val json = gson.toJson(list)
        sharedPrefs().edit().putString(key, json).apply()
        EventBus.getDefault().post(FavoriteScreenEvent())
    }

    private fun getFavoriteScreenSongIds(): MutableList<Int>? {
        if (itemIds == null) {
            val json = sharedPrefs().getString(FAVORITES_KEY, "")
            val type = object : TypeToken<MutableList<Int>>() {}.type
            itemIds = gson.fromJson<MutableList<Int>>(json, type) ?: return mutableListOf()
        }
        return itemIds
    }

    fun getFavScreenSongs(): List<Song> {
        val itemIds = getFavoriteScreenSongIds()
        val items = mutableListOf<Song>()
        itemIds?.let {
            itemIds.forEach {
                val item = SongsRepository.getSongById(it)
                item?.let {
                    items.add(item)
                }
            }
        }
        return items
    }

    fun favoritesListSize(): Int {
        val itemIds = getFavoriteScreenSongIds()
        itemIds?.let {
            return itemIds.size
        }
        return 0
    }

    fun addAllSongsToFavorite() {
        val itemIds = getFavoriteScreenSongIds()
        itemIds?.let {
            val songs = SongsRepository.getSongs()
            songs.forEach { song ->
                if (!song.isMarkedFavorite) {
                    addItem(song)
                }
            }
        }
    }

    fun clearFavoritesScreen() {
        val itemIds = getFavoriteScreenSongIds()
        itemIds?.let {
            itemIds.clear()
            val songs = SongsRepository.getSongs()
            songs.forEach { it.isMarkedFavorite = false }
            saveFavScreen(FAVORITES_KEY, itemIds)
        }
    }

    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(PlaylistApplication.getAppContext())
}