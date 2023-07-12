package id.muhammadfaisal.moviedb.api.repo

import id.muhammadfaisal.moviedb.api.service.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    fun getPopularMovies() = this.apiService.getPopularMovies()

    fun getGenres() = this.apiService.getGenres()

    fun getPopularMoviesByGenre(genreIds: String, page:Int) = this.apiService.getPopularMoviesByGenre(genreIds, page)

    fun getDetailMovie(movieId: Int) = this.apiService.getDetailMovie(movieId)

    fun getMovieReviews(movieId: Int, page: Int) = this.apiService.getMovieReviews(movieId, page)

    fun getMovieTrailer(movieId: Int) = this.apiService.getMovieTrailers(movieId)
}