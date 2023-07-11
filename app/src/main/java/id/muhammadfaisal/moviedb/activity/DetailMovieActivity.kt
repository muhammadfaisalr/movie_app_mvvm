package id.muhammadfaisal.moviedb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.muhammadfaisal.moviedb.R
import id.muhammadfaisal.moviedb.databinding.ActivityDetailMovieBinding

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailMovieBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
    }
}