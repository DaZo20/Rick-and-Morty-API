package da.zo.rickandmortyapi.episodes.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import da.zo.rickandmortyapi.R
import da.zo.rickandmortyapi.episodes.domain.model.Episode

//
// Created by DaZo20 on 22/02/2023.
// Copyright (c) 2023 DZ. All rights reserved.
//
class EpisodeAdapter (
    private val episodeData: MutableList<Episode> = mutableListOf()
        ): RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val rootView: View = LayoutInflater.from(parent.context).inflate(R.layout.episodes_adapter_row, parent, false)
        return EpisodeViewHolder(itemView = rootView)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) =
        holder.bindData(episodeData[position])


    override fun getItemCount(): Int = episodeData.size

    fun updateData(newData: List<Episode>) {
        episodeData.addAll(newData.toMutableList())
        notifyDataSetChanged()
    }

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView by lazy { itemView.findViewById(R.id.tv_name) }
        private val tvEpisode: TextView by lazy { itemView.findViewById(R.id.tv_episode) }
        private val tvAirDate: TextView by lazy { itemView.findViewById(R.id.tv_air_date) }

        fun bindData(episode: Episode) {
            tvName.text = episode.name
            tvEpisode.text = episode.episode
            tvAirDate.text = episode.airDate
        }
    }


}