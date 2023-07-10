package id.muhammadfaisal.moviedb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.moviedb.adapter.PopularMovieAdapter
import id.muhammadfaisal.moviedb.api.model.response.ResultsItem
import id.muhammadfaisal.moviedb.databinding.ActivityMainBinding
import id.muhammadfaisal.moviedb.vm.PopularMovieViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var popularMovieViewModel: PopularMovieViewModel
    private lateinit var movieAdapter: PopularMovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.setupViewModel()
        this.data()
        this.setupObserver()
        this.setupRecyclerView()

        this.popularMovieViewModel.moviesLiveData.observe(this, Observer { movies ->
            this.movieAdapter.setData(movies?.results!!)
        })

    }

    private fun setupRecyclerView() {
        this.movieAdapter = PopularMovieAdapter()
        this.binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        this.binding.recyclerView.adapter = movieAdapter
    }

    private fun data() {
        this.popularMovieViewModel.getPopularMovies()
    }

    private fun setupObserver() {
        with(this.popularMovieViewModel) {
            moviesLiveData.observe(this@MainActivity) {
                Toast.makeText(this@MainActivity, it.page!!.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewModel() {
        this.popularMovieViewModel = ViewModelProvider(this)[PopularMovieViewModel::class.java]
    }
}