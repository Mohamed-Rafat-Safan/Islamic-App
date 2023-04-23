package com.example.myislamicapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myislamicapp.data.HomeContent
import com.example.myislamicapp.data.model.prayers.City
import com.example.myislamicapp.databinding.ItemCitiesNameBinding
import com.example.myislamicapp.databinding.ItemHomeBinding
import com.example.myislamicapp.ui.prayersTime.CitiesAdapter

class HomeAdapter(val listContent:ArrayList<HomeContent>, val contentClickListener: ContentClickListener) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return listContent.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val content = listContent[position]

        holder.bind(content)

        holder.itemView.setOnClickListener {
            contentClickListener.onItemClick(content)
        }
    }

    inner class HomeViewHolder(val binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(homeContent: HomeContent) {
            binding.tvContentName.text = homeContent.contentName
            binding.ivContentImage.setImageResource(homeContent.contentImage)
        }
    }

    interface ContentClickListener {
        fun onItemClick(homeContent: HomeContent)
    }

}