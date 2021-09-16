package dev.vladimirj.tidal.base.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("goneUnless")
fun View.goneUnless(visible: Boolean) {
    this.visibility = if(visible) View.VISIBLE else View.GONE
}