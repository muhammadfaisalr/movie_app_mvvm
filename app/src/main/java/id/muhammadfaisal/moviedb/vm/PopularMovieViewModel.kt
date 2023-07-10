package id.muhammadfaisal.moviedb.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.muhammadfaisal.moviedb.api.model.response.PopularMoviesResponse
import id.muhammadfaisal.moviedb.api.repo.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(val repository: Repository): ViewModel() {
    val moviesLiveData = MutableLiveData<PopularMoviesResponse>()
    val error = MutableLiveData<String>()
    private lateinit var disposable: Disposable

    fun getPopularMovies() {
        disposable = repository.getRemoteData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                moviesLiveData.postValue(it)
            }, {
                error.postValue(it.toString())
            })
    }

    override fun onCleared() {
        super.onCleared()

        if (this::disposable.isInitialized) {
            disposable.dispose()
        }
    }
}