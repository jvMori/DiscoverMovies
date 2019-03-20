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

class CrewAdapter (
    private val crewList: List<Crew> = mutableListOf()
): RecyclerView.Adapter<CrewAdapter.CrewViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_item, parent, false)
        return CrewViewHolder(view)
    }

    override fun getItemCount(): Int = crewList.size

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
       holder.bindView(crewList[position])
    }

    class CrewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(crew : Crew){
            itemView.title.text = crew.name
            LoadImage.loadImage(itemView.context, itemView.icon, Const.base_poster_url + crew.profilePath)
        }
    }
}