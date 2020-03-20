/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.raywenderlich.android.foodmart.ui.detail

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
import com.raywenderlich.android.foodmart.R
import com.raywenderlich.android.foodmart.app.toast
import com.raywenderlich.android.foodmart.model.events.CartEvent
import com.raywenderlich.android.foodmart.ui.Injection
import kotlinx.android.synthetic.main.activity_food.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FoodActivity : AppCompatActivity(), SongsContract.View {

    override lateinit var presenter: SongsContract.Presenter

    companion object {
        private const val EXTRA_FOOD_ID = "place_id"

        fun newIntent(context: Context, foodId: Int): Intent {
            val intent = Intent(context, FoodActivity::class.java)
            intent.putExtra(EXTRA_FOOD_ID, foodId)
            return intent
        }
    }

    lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()
    private var pause:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = Injection.provideFoodPresenter(this)

        val songs = presenter.getFood(intent.extras.getInt(EXTRA_FOOD_ID))

        songs?.let {
            foodImage.setImageResource(resources.getIdentifier(songs.largeImage, null, packageName))
            collapsingToolbar.title = songs.name
            collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
            foodName.text = songs.name
            foodDescription.text = songs.description
            fab.setImageResource(if (songs.isInCart) R.drawable.play128 else R.drawable.ic_add)
            moreInfo.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(songs.link))
                startActivity(browserIntent)
            }

            youtubeTextview.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(songs.youtubeLink))
                startActivity(browserIntent)
            }

            fab.setOnClickListener {
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
                fab.isEnabled = false
                fabStop.isEnabled = true

                mediaPlayer.setOnCompletionListener {
                    fab.isEnabled = true
                    fabStop.isEnabled = false
                    Toast.makeText(this,"end",Toast.LENGTH_SHORT).show()
                }

            }
        }

        fabStop.setOnClickListener{
            if(mediaPlayer.isPlaying || pause.equals(true)){
                pause = false
                seek_bar.progress = 0
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                handler.removeCallbacks(runnable)

                fab.isEnabled = true
                fabStop.isEnabled = false
                tv_pass.text = ""
                tv_due.text = ""
                Toast.makeText(this,"media stop",Toast.LENGTH_SHORT).show()
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

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartEvent(event: CartEvent) {
        val food = presenter.getFood(intent.extras.getInt(EXTRA_FOOD_ID))
        val isInCart = food?.isInCart ?: false
        fab.setImageResource(if (isInCart) R.drawable.play128 else R.drawable.ic_add)
        toast(if (isInCart) getString(R.string.added_to_cart) else getString(R.string.removed_from_cart))
    }
}

val MediaPlayer.seconds:Int
    get() {
        return this.duration / 1000
    }
// Creating an extension property to get media player current position in seconds
val MediaPlayer.currentSeconds:Int
    get() {
        return this.currentPosition/1000
    }
