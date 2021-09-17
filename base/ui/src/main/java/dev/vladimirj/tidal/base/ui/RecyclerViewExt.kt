package dev.vladimirj.tidal.base.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addOnScrolledToBottom(onScrolledToBottom: () -> Unit) {

    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        private val visibleThreshold = 5

        private var loading = true
        private var previousTotal = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            when (layoutManager) {

                is LinearLayoutManager -> {
                    val linearLayoutManager = (layoutManager as LinearLayoutManager)
                    val visibleItemCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()

                    if (loading && totalItemCount > previousTotal) {

                        loading = false
                        previousTotal = totalItemCount
                    }

                    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

                        onScrolledToBottom()
                        loading = true
                    }
                }

                is GridLayoutManager -> {
                    val gridLayoutManager = (layoutManager as GridLayoutManager)
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    val firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()

                    if (loading && totalItemCount > previousTotal) {

                        loading = false
                        previousTotal = totalItemCount
                    }

                    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

                        onScrolledToBottom()
                        loading = true
                    }
                }
            }
        }
    })
}