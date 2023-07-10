package id.muhammadfaisal.moviedb.api.repo

import id.muhammadfaisal.moviedb.api.service.ApiService
import javax.inject.Inject

class Repository @Inject constructor(val apiService: ApiService) {
    fun getRemoteData() = apiService.getPopularMovies("5d2b1cddfabadcec3617f9caa845826b")
}