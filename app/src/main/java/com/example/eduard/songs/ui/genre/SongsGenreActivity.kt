package com.example.eduard.songs.ui.genre

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import com.example.eduard.songs.R
import com.example.eduard.songs.ui.Injection
import com.example.eduard.songs.ui.genre.SongsGenreContract.Presenter
import com.example.eduard.songs.ui.genre.SongsGenreContract.View
import kotlinx.android.synthetic.main.genre_screen_activity.*

class SongsGenreActivity : AppCompatActivity(), View {

    override lateinit var presenter: Presenter

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

    override fun showSongGenres(genres: List<String>) {
        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment =
                    GenreFragment.newInstance(genres[position])

            override fun getCount() = genres.size

            override fun getPageTitle(position: Int) = genres[position]
        }
    }
}
