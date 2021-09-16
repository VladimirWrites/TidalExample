package dev.vladimirj.tidal.search.ui.databinding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import dev.vladimirj.tidal.search.ui.BR

class DataBindingViewHolder<T>(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        binding.setVariable(BR.uiModel, item)
        binding.executePendingBindings()
    }
}