package id.muhammadfaisal.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.moviedb.R
import id.muhammadfaisal.moviedb.api.model.response.ResultsItem
import id.muhammadfaisal.moviedb.databinding.ItemMovieBinding

class PopularMovieAdapter : RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {
    var results = mutableListOf<ResultsItem>()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val binding = ItemMovieBinding.bind(view)

        fun bind(resultsItem: ResultsItem) {
            this.binding.textTitle.text = resultsItem.title
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularMovieAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: PopularMovieAdapter.ViewHolder, position: Int) {
        holder.bind(this.results[position])
    }

    override fun getItemCount(): Int {
        return this.results.size
    }

    fun setData(results: List<ResultsItem>) {
        this.results = results.toMutableList()
        notifyDataSetChanged()
    }
}