package id.muhammadfaisal.moviedb.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.muhammadfaisal.moviedb.api.model.response.GenresResponse
import id.muhammadfaisal.moviedb.api.model.response.PopularMoviesResponse
import id.muhammadfaisal.moviedb.api.repo.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val repository: Repository): ViewModel() {
    var moviesLiveData = MutableLiveData<PopularMoviesResponse>()
    val genres = MutableLiveData<GenresResponse>()

    private val selectedGenreIds = MutableLiveData<String>()
    private val error = MutableLiveData<String>()

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

    fun setSelectedGenre(genreIds: String) {
        this.selectedGenreIds.value = genreIds

        Log.d(MovieViewModel::class.java.simpleName, "Selected Genre: [$genreIds]")
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