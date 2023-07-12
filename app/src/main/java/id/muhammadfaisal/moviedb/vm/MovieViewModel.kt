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
import id.muhammadfaisal.moviedb.api.model.response.TrailerItem
import id.muhammadfaisal.moviedb.api.model.response.TrailerResponse
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

    var reviews = MutableLiveData<ReviewsResponse>()
    var updatedReviews = MutableLiveData<ReviewsResponse>()

    var trailers = MutableLiveData<TrailerResponse>()

    var genres = MutableLiveData<GenresResponse>()
    var detailMovie = MutableLiveData<DetailMovieResponse>()

    val error = MutableLiveData<String>()


    private val selectedGenreIds = MutableLiveData<String>()

    private lateinit var disposable: Disposable

    fun getPopularMoviesByGenre(genreIds: String, page: Int) {
        this.disposable = this.repository.getPopularMoviesByGenre(genreIds, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.moviesLiveData.postValue(it)
            }, {
                this.error.postValue(it.toString())
            })
    }

    fun loadMoreMovies(genreIds: String, page: Int) {
        this.disposable = this.repository.getPopularMoviesByGenre(genreIds, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.updatedMoviesData.postValue(it)
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

    fun getMovieReviews(movieId: Int, page: Int) {
        this.disposable = this.repository.getMovieReviews(movieId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.reviews.postValue(it)
            }, {
                this.error.postValue(it.toString())
            })
    }

    fun loadMoreReviews(movieId: Int, page: Int) {
        this.disposable = this.repository.getMovieReviews(movieId, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.updatedReviews.postValue(it)
            }, {
                this.error.postValue(it.toString())
            })
    }

    fun getMovieTrailer(movieId: Int) {
        this.disposable = this.repository.getMovieTrailer(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                this.trailers.postValue(it)
            }, {
                this.error.postValue(it.toString())
            })
    }

    fun getTrailerByPos(pos:Int) : TrailerItem? {
        return this.trailers.value?.results?.get(pos)
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