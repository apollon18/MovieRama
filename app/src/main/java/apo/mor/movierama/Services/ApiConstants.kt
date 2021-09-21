package apo.mor.movierama.Services

class ApiConstants {

    companion object {

        const val TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/"
        const val TMDB_SEARCH_URL = "https://api.themoviedb.org/3/search/"
        const val API_KEY = "30842f7c80f80bb3ad8a2fb98195544d"
        const val LANGUAGE = "en-US"
        const val IMAGES_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w780/"
        const val IMAGES_BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w1280/"

        const val HTTP_READ_TIMEOUT = 30
        const val HTTP_CONNECT_TIMEOUT = 30
    }
}