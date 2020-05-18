package com.example.eduard.songs.model


data class Song(
        val id: Int,
        val name: String,
        val image: String,
        val description: String,
        val link: String,
        val category: String,
        val youtubeLink: String,
        var isMarkedFavorite: Boolean = false) {
    val thumbnail: String
    get() = "drawable/$image"

  val largeImage: String
    get() = "drawable/$image"
}