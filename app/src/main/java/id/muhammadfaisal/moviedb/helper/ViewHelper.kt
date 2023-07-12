package id.muhammadfaisal.moviedb.helper

import android.view.View

class ViewHelper {
    companion object {
        fun gone(vararg views: View) {
            for (view in views){
                view.visibility = View.GONE
            }
        }

        fun visible(vararg views: View) {
            for (view in views) {
                view.visibility = View.VISIBLE
            }
        }
    }
}