package id.muhammadfaisal.moviedb.api.service

import id.muhammadfaisal.moviedb.api.model.response.DetailMovieResponse
import id.muhammadfaisal.moviedb.api.model.response.GenresResponse
import id.muhammadfaisal.moviedb.api.model.response.PopularMoviesResponse
import id.muhammadfaisal.moviedb.api.model.response.ReviewsResponse
import id.muhammadfaisal.moviedb.api.model.response.TrailerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovies(): Single<PopularMoviesResponse>

    @GET("movie/popular")
    fun getPopularMoviesByGenre(@Query("with_genres") genreIds: String, @Query("page") page: Int): Single<PopularMoviesResponse>

    @GET("movie/{id}")
    fun getDetailMovie(@Path("id") movieId: Int): Single<DetailMovieResponse>

    @GET("movie/{id}/reviews")
    fun getMovieReviews(@Path("id") movieId: Int, @Query("page") page: Int): Single<ReviewsResponse>

    @GET("movie/{id}/videos")
    fun getMovieTrailers(@Path("id") movieId: Int): Single<TrailerResponse>

    @GET("genre/movie/list")
    fun getGenres(): Single<GenresResponse>
}