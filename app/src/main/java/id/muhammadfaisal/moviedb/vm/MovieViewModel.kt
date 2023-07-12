package id.muhammadfaisal.moviedb.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.resource.bitmap.CenterInside
import dagger.hilt.android.lifecycle.HiltViewModel
import id.muhammadfaisal.moviedb.api.model.response.DetailMovieResponse
import id.muhammadfaisal.moviedb.api.model.response.GenresResponse
import id.muhammadfaisal.moviedb.api.model.response.PopularMoviesResponse
import id.muhammadfaisal.moviedb.api.model.response.ResultsReview
import id.muhammadfaisal.moviedb.api.model.response.ReviewsResponse
import id.muhammadfaisal.moviedb.api.repo.Repository
import id.muhammadfaisal.moviedb.util.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val repository: Repository): ViewModel() {
    var moviesLiveData = MutableLiveData<PopularMoviesResponse>()
    var updatedMoviesData = MutableLiveData<PopularMoviesResponse>()

    val genres = MutableLiveData<GenresResponse>()
    var detailMovie = MutableLiveData<DetailMovieResponse>()
    var reviews = MutableLiveData<ReviewsResponse>()

    private val selectedGenreIds = MutableLiveData<String>()
    private val error = MutableLiveData<String>()

    private lateinit var disposable: Disposable

    fun getPopularMoviesByGenre(genreIds: String, page: Int, state: Int) {
        this.disposable = this.repository.getPopularMoviesByGenre(genreIds, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (state == Constant.State.NEW) {
                    this.moviesLiveData.postValue(it)
                } else {
                    this.updatedMoviesData.postValue(it)
                }
            }, {
                this.error.postValue(it.toString())
            })
    }

    fun getGenres() {
        this.disposable = this.repository.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                this.genres.postValue(it)
            }, {
                this.error.postValue(it.toString())
            })
    }

    fun getDetailMovie(movieId: Int) {
        this.disposable = this.repository.getDetailMovie(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.detailMovie.postValue(it)
            }, {
                this.error.postValue(it.toString())
            })
    }

    fun getMovieReviews(movieId: Int) {
        this.disposable = this.repository.getMovieReviews(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.reviews.postValue(it)
            }, {
                this.error.postValue(it.toString())
            })
    }

    fun setSelectedGenre(genreIds: String) {
        this.selectedGenreIds.value = genreIds

        Log.d(MovieViewModel::class.java.simpleName, "Selected Genre: [$genreIds]")
    }

    fun getReviewByPos(pos:Int) : ResultsReview {
        return this.reviews.value?.results?.get(pos)!!
    }

    fun getSelectedGenre(): String {
        return this.selectedGenreIds.value.toString()
    }

    override fun onCleared() {
        super.onCleared()

        if (this::disposable.isInitialized) {
            disposable.dispose()
        }
    }
}