package com.raywenderlich.android.foodmart.app

import android.app.Application
import android.content.Context
import com.raywenderlich.android.foodmart.model.SongsRepository


class PlaylistApplication : Application() {

  companion object {
    private lateinit var instance: PlaylistApplication

    fun getAppContext(): Context = instance.applicationContext
  }

  override fun onCreate() {
    instance = this
    super.onCreate()

    // Initialize FoodRepository
    SongsRepository.loadFoods(this)
  }
}