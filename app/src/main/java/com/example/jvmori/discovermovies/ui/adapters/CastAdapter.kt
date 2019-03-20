package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.data.network.response.credits.Cast

class CastAdapter(
    private val cast : List<Cast> = mutableListOf()
) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
      val view = LayoutInflater.from(parent.context).inflate()
    }

    override fun getItemCount(): Int = cast.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {

    }

    class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}