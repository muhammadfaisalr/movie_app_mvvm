package id.muhammadfaisal.moviedb.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.muhammadfaisal.moviedb.R
import id.muhammadfaisal.moviedb.api.model.response.ResultsReview
import id.muhammadfaisal.moviedb.databinding.ItemReviewBinding
import id.muhammadfaisal.moviedb.util.Constant
import id.muhammadfaisal.moviedb.vm.MovieViewModel

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    var reviews = mutableListOf<ResultsReview>()

    private lateinit var context: Context
    private lateinit var movieViewModel: MovieViewModel

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var binding = ItemReviewBinding.bind(view)

        fun bind(movieViewModel: MovieViewModel, position: Int) {
            val resultReview = movieViewModel.getReviewByPos(position)
            this.binding.viewModel = movieViewModel
            this.binding.item = resultReview

            this.binding.textQuote.text = Html.fromHtml(
                resultReview.content,
                Html.FROM_HTML_MODE_COMPACT
            ).toString()

            val avatarPath = resultReview.authorDetails.avatarPath
            if (avatarPath != null) {
                var imgUrl = avatarPath

                if (!avatarPath.contains("https")) {
                    imgUrl = Constant.IMAGE_URL + resultReview.authorDetails.avatarPath
                } else {
                    if (avatarPath.first() == '/') {
                        imgUrl = avatarPath.replaceFirst("/", "", false)
                    }
                }

                Glide.with(binding.imageUser)
                    .load(imgUrl)
                    .into(binding.imageUser)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.bind(this.movieViewModel, position)
    }

    override fun getItemCount(): Int {
        return this.reviews.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(context: Context, reviews: List<ResultsReview>) {
        this.reviews = reviews.toMutableList()
        this.context = context
        this.notifyDataSetChanged()
    }

    fun setViewModel(movieViewModel: MovieViewModel) {
        this.movieViewModel = movieViewModel
    }
}