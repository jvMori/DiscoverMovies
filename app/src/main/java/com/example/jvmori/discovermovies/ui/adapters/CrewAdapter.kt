package com.example.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jvmori.discovermovies.R
import com.example.jvmori.discovermovies.data.network.response.credits.Crew
import com.example.jvmori.discovermovies.util.Const
import com.example.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.cast_item.view.*

class CrewAdapter : BaseAdapter<Crew>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Crew> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false)
        return CrewViewHolder(view)
    }

    class CrewViewHolder(itemView: View) : BaseViewHolder<Crew>(itemView){
        override fun bindView(item : Crew){
            itemView.title.text = item.name
            LoadImage.loadImage(itemView.context, itemView.icon, Const.base_poster_url + item.profilePath)
        }
    }
}