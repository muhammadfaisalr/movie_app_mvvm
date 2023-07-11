package id.muhammadfaisal.moviedb.api.service

import id.muhammadfaisal.moviedb.api.model.response.GenresResponse
import id.muhammadfaisal.moviedb.api.model.response.PopularMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovies(): Single<PopularMoviesResponse>

    @GET("movie/popular")
    fun getPopularMoviesByGenre(@Query("with_genres") genreIds: String, @Query("page") page: Int): Single<PopularMoviesResponse>

    @GET("genre/movie/list")
    fun getGenres(): Single<GenresResponse>
}