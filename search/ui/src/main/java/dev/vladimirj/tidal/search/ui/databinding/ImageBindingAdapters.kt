package dev.vladimirj.tidal.search.ui.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(imageUrl: String?) {
    setImageDrawable(null)
    if(!imageUrl.isNullOrBlank()) {
        Glide.with(this)
            .load(imageUrl)
            .into(this)
    }
}

@BindingAdapter("roundImageUrl")
fun ImageView.roundImageUrl(imageUrl: String?) {
    setImageDrawable(null)
    if(!imageUrl.isNullOrBlank()) {
        Glide.with(this)
            .load(imageUrl)
            .circleCrop()
            .into(this)
    }
}