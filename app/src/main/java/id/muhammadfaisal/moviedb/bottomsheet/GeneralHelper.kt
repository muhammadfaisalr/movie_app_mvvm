package id.muhammadfaisal.moviedb.bottomsheet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import id.muhammadfaisal.moviedb.util.Constant

class GeneralHelper {
    companion object {
        fun move(context: Context, clz:Class<*>, bundle: Bundle?, isForgettable: Boolean) {
            val intent = Intent(context, clz)

            if (isForgettable) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }

            if (bundle != null) {
                intent.putExtra(Constant.Key.BUNDLING, bundle)
            }

            context.startActivity(intent)
        }
    }
}