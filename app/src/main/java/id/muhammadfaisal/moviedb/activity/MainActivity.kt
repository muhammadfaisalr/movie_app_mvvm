package id.muhammadfaisal.moviedb.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.moviedb.R
import id.muhammadfaisal.moviedb.adapter.PopularMovieAdapter
import id.muhammadfaisal.moviedb.api.model.response.PopularMoviesResponse
import id.muhammadfaisal.moviedb.api.model.response.ResultsItem
import id.muhammadfaisal.moviedb.api.model.response.ResultsReview
import id.muhammadfaisal.moviedb.bottomsheet.FilterBottomSheetDialogFragment
import id.muhammadfaisal.moviedb.databinding.ActivityMainBinding
import id.muhammadfaisal.moviedb.helper.ViewHelper
import id.muhammadfaisal.moviedb.listener.BottomSheetListener
import id.muhammadfaisal.moviedb.util.Constant
import id.muhammadfaisal.moviedb.vm.MovieViewModel

@ExperimentalBadgeUtils
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomSheetListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: PopularMovieAdapter
    private lateinit var badgeDrawable: BadgeDrawable

    private var genreIds = ""
    private var page = 1;

    private lateinit var movies: ArrayList<ResultsItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.initialize()
        this.setupViewModel()
        this.data()
        this.setupRecyclerView()

    }

    private fun setupRecyclerView() {
        this.binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        this.binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                RecyclerView.VERTICAL
            )
        )
        this.movieAdapter = PopularMovieAdapter(this)
        this.binding.recyclerView.adapter = this.movieAdapter
    }

    private fun data() {
        this.movieViewModel.getPopularMoviesByGenre(this.genreIds, this.page)
        this.movieViewModel.moviesLiveData.observe(this) { movies ->
            this.movieAdapter.setData(movies.results!!)
            ViewHelper.gone(this.binding.progressBar)
        }

        this.movieViewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            ViewHelper.gone(this.binding.progressBar)
        }
    }

    fun addData() {
        this.page++
        this.movieViewModel.loadMoreMovies(this.genreIds, this.page)
        this.movieViewModel.updatedMoviesData.observe(this) { movies ->
            val results = movies.results!!
            val lMovies = ArrayList(results)
            results.clear()

            for (movie in lMovies) {
                Log.d(MainActivity::class.simpleName, "Adding Movie to list [${movie.title}]")
                this.movieViewModel.moviesLiveData.value?.results?.add(movie)
                this.binding.recyclerView.adapter?.notifyDataSetChanged()
            }

            ViewHelper.gone(this.binding.progressBar)
        }
    }

    private fun setupViewModel() {
        this.movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
    }

    private fun initialize() {
        this.badgeDrawable = BadgeDrawable.create(this)
        this.movies = ArrayList()

        var continueScroll = true
        this.binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {

                    if (continueScroll) {
                        ViewHelper.visible(binding.progressBar)
                        continueScroll = false
                    }

                    Handler(Looper.getMainLooper()).postDelayed({
                        continueScroll = true
                        addData()
                    }, 2000L)

                }

            }
        })

        this.binding.exfabFilter.setOnClickListener {
            val bottomSheet = FilterBottomSheetDialogFragment(this)
            bottomSheet.show(this.supportFragmentManager, MainActivity::class.java.simpleName)
        }
    }

    override fun onDismissed() {
        this.genreIds = this.movieViewModel.getSelectedGenre()

        if (this.genreIds != "") {
            val badgeNumber = genreIds.split(",").size

            this.badgeDrawable.backgroundColor = this.getColor(R.color.black_text)
            this.badgeDrawable.number = badgeNumber
            this.badgeDrawable.badgeTextColor = this.getColor(R.color.white)

            BadgeUtils.attachBadgeDrawable(this.badgeDrawable, this.binding.exfabFilter)
        } else {
            BadgeUtils.detachBadgeDrawable(this.badgeDrawable, this.binding.exfabFilter)
        }

        this.data()
        this.setupRecyclerView()
    }
}