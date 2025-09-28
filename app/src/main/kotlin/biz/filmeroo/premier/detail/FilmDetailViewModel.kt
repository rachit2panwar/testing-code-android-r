package biz.filmeroo.premier.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import biz.filmeroo.premier.api.ApiFilm
import biz.filmeroo.premier.api.SimilarMovieResponse
import biz.filmeroo.premier.base.BaseViewModel
import biz.filmeroo.premier.main.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
internal class FilmDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    filmRepository: FilmRepository
) : BaseViewModel() {

    private val filmId: Long = savedStateHandle.get<Long>(FILM_ID)!!
    private val _filmDetailState = MutableLiveData<FilmState<ApiFilm>>()
    private val _filmSimilarState = MutableLiveData<FilmState<SimilarMovieResponse>>()

    val filmDetailState: LiveData<FilmState<ApiFilm>>
        get() = _filmDetailState

    val filmSimilarState: LiveData<FilmState<SimilarMovieResponse>>
        get() = _filmSimilarState

    init {
        addSubscription(
            filmRepository.fetchMovie(filmId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _filmDetailState.value = FilmState.Success(it) },
                    { _filmDetailState.value = FilmState.Error }
                )
        )

        addSubscription(
            filmRepository.fetchSimilarMovies(filmId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _filmSimilarState.value = FilmState.Success(it) },
                    {
                        Log.d("Abhishek", ":Exception is $it ")
                        _filmSimilarState.value = FilmState.Error
                    }
                )
        )
    }

    internal companion object {
        internal const val FILM_ID = "filmId"
    }

    sealed class FilmState<out T> {
        data class Success<out T>(val data: T) : FilmState<T>()
        data object Error : FilmState<Nothing>()
    }
}
