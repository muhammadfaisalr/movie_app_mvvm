package id.muhammadfaisal.moviedb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewTreeObserver
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.moviedb.adapter.ReviewAdapter
import id.muhammadfaisal.moviedb.adapter.TrailerAdapter
import id.muhammadfaisal.moviedb.databinding.ActivityDetailMovieBinding
import id.muhammadfaisal.moviedb.helper.ViewHelper
import id.muhammadfaisal.moviedb.util.Constant
import id.muhammadfaisal.moviedb.vm.MovieViewModel

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding

    private lateinit var viewModel: MovieViewModel
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var trailerAdapter: TrailerAdapter

    private var movieId = 0
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailMovieBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.extract()
        this.initialize()
        this.setupVideModel()
        this.data()
    }

    private fun initialize() {
        this.reviewAdapter = ReviewAdapter()
        this.trailerAdapter = TrailerAdapter()

        this.binding.let {
            it.recyclerReview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            it.recyclerReview.adapter = this.reviewAdapter
            it.recyclerReview.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

            it.recyclerTrailer.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
            it.recyclerTrailer.adapter = this.trailerAdapter
        }
        this.binding.imageBack.setOnClickListener {
            finish()
        }
    }

    private fun extract() {
        val bundle = this.intent.getBundleExtra(Constant.Key.BUNDLING)

        if (bundle != null) {
            this.movieId = bundle.getInt(Constant.Key.MOVIE_ID)
        }
    }

    private fun setupVideModel() {
        this.viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
    }

    private fun data() {
        this.viewModel.getDetailMovie(this.movieId)
        this.viewModel.detailMovie.observe(this) {
            this.binding.viewModel = this.viewModel

            if (it.posterPath != null) {
                Glide.with(this)
                    .load(Constant.IMAGE_URL + it.posterPath)
                    .into(this.binding.imageMovie)
            }
        }

        this.viewModel.getMovieReviews(this.movieId, this.page)
        this.viewModel.reviews.observe(this) {
            this.reviewAdapter.setViewModel(this.viewModel)

            if (it.results != null) {
                this.reviewAdapter.setData(this, it.results)

                if (it.results.isNotEmpty()) {
                    ViewHelper.gone(this.binding.textEmpty)
                }
            }
            ViewHelper.gone(binding.progressBar)
        }

        this.viewModel.getMovieTrailer(this.movieId)
        this.viewModel.trailers.observe(this) {
            this.trailerAdapter.setViewModel(this.viewModel)
            if (it.results != null) {
                this.trailerAdapter.setData(this, it.results)
            }
        }

        var canContinueScroll = true

        this.binding.recyclerReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    if (canContinueScroll) {
                        canContinueScroll = false
                        ViewHelper.visible(binding.progressBar)
                    }

                    Handler(Looper.getMainLooper()).postDelayed({
                        canContinueScroll = true
                        loadMoreReviews()
                    }, 2000L)
                }
            }
        })
    }

    private fun loadMoreReviews() {
        this.page++
        this.viewModel.loadMoreReviews(this.movieId, this.page)
        this.viewModel.updatedReviews.observe(this) { reviews ->
            val results = reviews.results

            if (results != null) {
                val lReview = ArrayList(results)
                results.clear()

                for (review in lReview) {
                    Log.d(DetailMovieActivity::class.simpleName, "Adding Movie to list [${review.content}]")
                    this.viewModel.reviews.value?.results?.add(review)
                    this.binding.recyclerReview.adapter?.notifyDataSetChanged()
                }

                ViewHelper.gone(this.binding.progressBar)
            }
        }
    }
}