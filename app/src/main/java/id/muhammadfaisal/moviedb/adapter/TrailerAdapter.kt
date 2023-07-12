package id.muhammadfaisal.moviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import id.muhammadfaisal.moviedb.R
import id.muhammadfaisal.moviedb.api.model.response.TrailerItem
import id.muhammadfaisal.moviedb.databinding.ItemTrailerBinding
import id.muhammadfaisal.moviedb.vm.MovieViewModel

class TrailerAdapter : RecyclerView.Adapter<TrailerAdapter.ViewHolder>() {

    var trailers = mutableListOf<TrailerItem>()

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemTrailerBinding.bind(view)
        private lateinit var context: Context

        fun bind(context: Context, movieViewModel: MovieViewModel, position: Int) {
            this.context = context
            val trailerItem = movieViewModel.getTrailerByPos(position)

            this.binding.ytPlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(trailerItem?.key!!, 0F)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false))
    }

    override fun onBindViewHolder(holder: TrailerAdapter.ViewHolder, position: Int) {
        holder.bind(this.context, this.movieViewModel, position)
    }

    override fun getItemCount(): Int {
        return this.trailers.size
    }

    fun setData(context: Context, trailers: List<TrailerItem>) {
        this.context = context
        this.trailers = trailers.toMutableList()
        notifyDataSetChanged()
    }

    fun setViewModel(movieViewModel: MovieViewModel) {
        this.movieViewModel = movieViewModel
    }
}