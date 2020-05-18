package com.example.eduard.songs.ui.play_song_screen

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import android.widget.Toast
import com.example.eduard.songs.R
import com.example.eduard.songs.app.toast
import com.example.eduard.songs.model.events.FavoriteScreenEvent
import com.example.eduard.songs.ui.Injection
import com.example.eduard.songs.ui.play_song_screen.PlaySongContract.Presenter
import com.example.eduard.songs.ui.play_song_screen.PlaySongContract.View
import kotlinx.android.synthetic.main.play_song_screen.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PlaySongActivity : AppCompatActivity(), View {

    override lateinit var presenter: Presenter

    companion object {
        private const val EXTRA_SONG_ID = "place_id"

        fun newIntent(context: Context, foodId: Int): Intent {
            val intent = Intent(context, PlaySongActivity::class.java)
            intent.putExtra(EXTRA_SONG_ID, foodId)
            return intent
        }
    }

    lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private var handler: Handler = Handler()
    private var pause: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_song_screen)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = Injection.provideFoodPresenter(this)

        val songs = presenter.getSong(intent.extras.getInt(EXTRA_SONG_ID))

        songs?.let {
            songImage.setImageResource(resources.getIdentifier(songs.largeImage, null, packageName))
            collapsingToolbar.title = songs.name
            collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
            songName.text = songs.name
            lyrics.text = songs.description

            spotifyLink.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(songs.link))
                startActivity(browserIntent)
            }

            youtubeTextview.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(songs.youtubeLink))
                startActivity(browserIntent)
            }

            fabPlay.setOnClickListener {
                if (songs.id == 1) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.hold_the_line)
                    mediaPlayer.start()
                }
                if (songs.id == 2) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.db)
                    mediaPlayer.start()
                }
                if (songs.id == 3) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.eastside)
                    mediaPlayer.start()
                }
                if (songs.id == 4) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.rene)
                    mediaPlayer.start()
                }
                if (songs.id == 5) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.lost_frequencies_send_her_my_love_ro_remix)
                    mediaPlayer.start()
                }
                if (songs.id == 6) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.show_me_love)
                    mediaPlayer.start()
                }
                if (songs.id == 7) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.summer_days)
                    mediaPlayer.start()
                }
                if (songs.id == 8) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.tired)
                    mediaPlayer.start()
                }
                if (songs.id == 9) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.una_vaina_loca)
                    mediaPlayer.start()
                }
                if (songs.id == 10) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.red_lights)
                    mediaPlayer.start()
                }
                if (songs.id == 11) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.faded)
                    mediaPlayer.start()
                }
                if (songs.id == 12) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.these_are_the_times)
                    mediaPlayer.start()
                }
                if (songs.id == 13) {
                    mediaPlayer = MediaPlayer.create(applicationContext, R.raw.fades_away)
                    mediaPlayer.start()
                }
                Toast.makeText(this, "media playing", Toast.LENGTH_SHORT).show()
                initializeSeekBar()
                fabPlay.isEnabled = false
                fabStop.isEnabled = true

                mediaPlayer.setOnCompletionListener {
                    fabPlay.isEnabled = true
                    fabStop.isEnabled = false
                    Toast.makeText(this, "end", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fabStop.setOnClickListener {
            if (mediaPlayer.isPlaying || pause.equals(true)) {
                pause = false
                seek_bar.progress = 0
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                handler.removeCallbacks(runnable)

                fabPlay.isEnabled = true
                fabStop.isEnabled = false
                tv_pass.text = ""
                tv_due.text = ""
                Toast.makeText(this, "media stop", Toast.LENGTH_SHORT).show()
            }
        }

        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

    }

    private fun initializeSeekBar() {
        seek_bar.max = mediaPlayer.seconds

        runnable = Runnable {
            seek_bar.progress = mediaPlayer.currentSeconds

            tv_pass.text = "${mediaPlayer.currentSeconds} sec"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            tv_due.text = "$diff sec"

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }
//Todo: not a todo, just remember to not remove the onFavoriteScreenEvent or else madness happens

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFavoriteScreenEvent(event: FavoriteScreenEvent) {
        val song = presenter.getSong(intent.extras.getInt(EXTRA_SONG_ID))
        val isMarkedFavorite = song?.isMarkedFavorite ?: false
        toast(if (isMarkedFavorite) getString(R.string.song_added_to_favorite) else getString(R.string.song_removed_from_favorite))
    }
}

val MediaPlayer.seconds: Int
    get() {
        return this.duration / 1000
    }

// Creating an extension property to get media player current position in seconds
val MediaPlayer.currentSeconds: Int
    get() {
        return this.currentPosition / 1000
    }
