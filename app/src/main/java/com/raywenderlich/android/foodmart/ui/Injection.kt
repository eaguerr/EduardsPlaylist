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

package com.raywenderlich.android.foodmart.ui

import com.raywenderlich.android.foodmart.model.FavoriteScreen
import com.raywenderlich.android.foodmart.model.SongsRepository
import com.raywenderlich.android.foodmart.ui.cart.FavoriteScreenContract
import com.raywenderlich.android.foodmart.ui.cart.FavoriteScreenPresenter
import com.raywenderlich.android.foodmart.ui.categories.SongsGenreContract
import com.raywenderlich.android.foodmart.ui.categories.SongsGenrePresenter
import com.raywenderlich.android.foodmart.ui.categories.GenreContract
import com.raywenderlich.android.foodmart.ui.categories.GenrePresenter
import com.raywenderlich.android.foodmart.ui.play_song_screen.PlaySongContract
import com.raywenderlich.android.foodmart.ui.play_song_screen.PlaySongPresenter
import com.raywenderlich.android.foodmart.ui.songs.SongsContract
import com.raywenderlich.android.foodmart.ui.songs.SongsPresenter


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