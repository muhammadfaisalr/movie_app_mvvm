package id.muhammadfaisal.moviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.muhammadfaisal.moviedb.R
import id.muhammadfaisal.moviedb.api.model.response.ResultsItem
import id.muhammadfaisal.moviedb.databinding.ItemMovieBinding

class PopularMovieAdapter(private val context: Context) : RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {
    var results = mutableListOf<ResultsItem>()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val binding = ItemMovieBinding.bind(view)
        fun bind(context: Context, resultsItem: ResultsItem) {
            val imgUrl = "https://image.tmdb.org/t/p/w500${resultsItem.posterPath}"

            Glide.with(context)
                .load(imgUrl)
                .into(binding.imageMovie)

            this.binding.textTitle.text = resultsItem.title
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context, this.results[position])
    }

    override fun getItemCount(): Int {
        return this.results.size
    }

    fun setData(results: List<ResultsItem>) {
        this.results = results.toMutableList()
        notifyDataSetChanged()
    }

    fun addData(movie: ResultsItem) {
        this.results.add(movie)
        notifyDataSetChanged()
    }
}