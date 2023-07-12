package id.muhammadfaisal.moviedb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.moviedb.R
import id.muhammadfaisal.moviedb.adapter.ReviewAdapter
import id.muhammadfaisal.moviedb.databinding.ActivityDetailMovieBinding
import id.muhammadfaisal.moviedb.util.Constant
import id.muhammadfaisal.moviedb.vm.MovieViewModel

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding

    private lateinit var viewModel: MovieViewModel
    private lateinit var reviewAdapter: ReviewAdapter

    private var movieId = 0

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
        this.binding.let {
            it.recyclerReview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            it.recyclerReview.adapter = this.reviewAdapter
            it.recyclerReview.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
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

            Glide.with(this)
                .load(Constant.IMAGE_URL + it.posterPath)
                .into(this.binding.imageMovie)
        }

        this.viewModel.getMovieReviews(this.movieId)
        this.viewModel.reviews.observe(this) {
            this.reviewAdapter.setViewModel(this.viewModel)
            this.reviewAdapter.setData(this, it.results!!)
        }
    }
}