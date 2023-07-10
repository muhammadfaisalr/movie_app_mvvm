package id.muhammadfaisal.moviedb.api.service

import id.muhammadfaisal.moviedb.api.model.response.PopularMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Single<PopularMoviesResponse>
}