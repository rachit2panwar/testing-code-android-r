package biz.filmeroo.premier.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import biz.filmeroo.premier.R
import biz.filmeroo.premier.api.ApiFilm
import biz.filmeroo.premier.api.FilmService
import biz.filmeroo.premier.api.SimilarMovie
import biz.filmeroo.premier.api.SimilarMovieResponse
import biz.filmeroo.premier.databinding.ActivityDetailBinding
import biz.filmeroo.premier.main.SimilarFilmAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilmDetailActivity : AppCompatActivity() {

    @Inject
    internal lateinit var picasso: Picasso

    @Inject
    internal lateinit var similarFilmAdapter: SimilarFilmAdapter

    private lateinit var binding: ActivityDetailBinding

    private val filmDetailViewModel by viewModels<FilmDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
        filmDetailViewModel.filmDetailState.observe(this, ::updateState)
        filmDetailViewModel.filmSimilarState.observe(this, ::updateSimilarMovieState)
    }

    private fun updateState(filmDetailState: FilmDetailViewModel.FilmState<ApiFilm>) {
        when (filmDetailState) {
            is FilmDetailViewModel.FilmState.Success -> displayMovie(filmDetailState.data)
            FilmDetailViewModel.FilmState.Error -> displayError()
        }
    }

    private fun updateSimilarMovieState(similarFilmState: FilmDetailViewModel.FilmState<SimilarMovieResponse>) {
        when (similarFilmState) {
            is FilmDetailViewModel.FilmState.Success -> displaySimilarMovie(similarFilmState.data.results)
            FilmDetailViewModel.FilmState.Error -> displayError()
        }
    }

    private fun displaySimilarMovie(data: List<SimilarMovie>) {
        binding.similarMoviesRecyclerView.apply {
            adapter = similarFilmAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@FilmDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        similarFilmAdapter.submitList(data)
    }

    private fun displayMovie(movie: ApiFilm) {
        if (movie.backdropPath != null) {
            picasso.load(FilmService.buildImageUrl(movie.backdropPath)).into(binding.filmImage)
        }
        binding.filmTitle.text = movie.title
        binding.filmOverview.text = movie.overview
    }

    private fun displayError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun start(origin: Activity, id: Long) {
            origin.startActivity(
                Intent(origin, FilmDetailActivity::class.java).apply {
                    putExtra(FilmDetailViewModel.FILM_ID, id)
                }
            )
        }
    }
}
