package id.muhammadfaisal.moviedb.util

class Constant {
    companion object {
        val BASE_URL = "https://api.themoviedb.org/3/"
        val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    class State {
        companion object {
            val UPDATE = 1
            val NEW = 0
        }
    }
    class Key {
        companion object {
            const val BUNDLING = "BUNDLING"
            const val MOVIE_ID = "MOVIE_ID"
            const val DETAIL_MOVIE = "DETAIL_MOVIE"
        }
    }
}