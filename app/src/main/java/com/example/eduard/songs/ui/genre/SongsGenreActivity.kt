package com.example.eduard.songs.ui.genre

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.example.eduard.songs.R
import com.example.eduard.songs.ui.Injection
import kotlinx.android.synthetic.main.genre_screen_activity.*

class SongsGenreActivity : AppCompatActivity(), SongsGenreContract.View {

  override lateinit var presenter: SongsGenreContract.Presenter

  companion object {
    fun newIntent(context: Context) =
      Intent(context, SongsGenreActivity::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.genre_screen_activity)

    presenter = Injection.provideCategoriesPresenter(this)

    title = getString(R.string.song_genres)

    tabLayout.setupWithViewPager(viewPager)
  }

  override fun onResume() {
    super.onResume()
    presenter.start()
  }

  override fun showCategories(categories: List<String>) {
    viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
      override fun getItem(position: Int): Fragment =
          GenreFragment.newInstance(categories[position])

      override fun getCount() = categories.size

      override fun getPageTitle(position: Int) = categories[position]
    }
  }
}
