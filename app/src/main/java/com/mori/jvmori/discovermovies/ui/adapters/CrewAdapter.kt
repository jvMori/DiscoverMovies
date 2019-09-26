package com.mori.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.network.response.credits.Crew
import com.mori.jvmori.discovermovies.util.Const
import com.mori.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.crew_item.view.*

class CrewAdapter : BaseAdapter<Crew>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Crew> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crew_item, parent, false)
        return CrewViewHolder(view)
    }

    class CrewViewHolder(itemView: View) : BaseViewHolder<Crew>(itemView) {
        override fun bindView(item: Crew) {
            itemView.apply {
                titleCrew.text = item.name
                crewMember.text = item.job
                LoadImage.loadImage(context, profile_image, Const.base_poster_url + item.profilePath)
            }
        }
    }
}