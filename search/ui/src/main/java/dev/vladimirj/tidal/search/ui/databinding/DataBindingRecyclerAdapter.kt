package dev.vladimirj.tidal.search.ui.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class DataBindingRecyclerAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBindingViewHolder<T>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DataBindingViewHolder<T>,
        position: Int
    ) = holder.bind(getItem(position))

    @LayoutRes
    abstract override fun getItemViewType(position: Int): Int
}