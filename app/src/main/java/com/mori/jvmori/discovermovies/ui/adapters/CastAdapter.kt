package com.mori.jvmori.discovermovies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mori.jvmori.discovermovies.R
import com.mori.jvmori.discovermovies.data.network.response.credits.Cast
import com.mori.jvmori.discovermovies.util.Const
import com.mori.jvmori.discovermovies.util.LoadImage
import kotlinx.android.synthetic.main.crew_item.view.*

class CastAdapter : BaseAdapter<Cast>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Cast> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crew_item, parent, false)
        return CastViewHolder(view)
    }

    class CastViewHolder(itemView: View) : BaseViewHolder<Cast>(itemView) {
        override fun bindView(item: Cast) {
            itemView.apply {
                titleCrew.text = item.name
                crewMember.text = item.character
                LoadImage.loadImage(context, profile_image, Const.base_poster_url + item.profilePath)
            }
        }
    }
}