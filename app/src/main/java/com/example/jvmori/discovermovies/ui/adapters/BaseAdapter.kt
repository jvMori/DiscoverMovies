package com.example.jvmori.discovermovies.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {

    private var items: List<T> = mutableListOf()
    var iOnItemClickListener: IOnItemClickListener<T>? = null

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindView(items[position])
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindView(item: T)
    }

    fun setItems(items: List<T>?) {
        this.items = items ?: mutableListOf()
        notifyDataSetChanged()
    }

    fun getItems() : List<T> = items

    interface IOnItemClickListener<T> {
        fun onItemClicked(item: T)
    }
}
