package com.example.eduard.songs.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.eduard.songs.ui.songs.SongsActivity

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val intent = Intent(this, SongsActivity::class.java)
    startActivity(intent)
    finish()
  }
}
