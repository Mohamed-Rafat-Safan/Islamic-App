package com.example.myislamicapp.ui.rosary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myislamicapp.data.model.Rosary
import com.example.myislamicapp.data.model.prayers.PrayersTiming
import com.example.myislamicapp.databinding.ItemSlideContainerBinding

class RosaryAdapter (private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<RosaryAdapter.RosaryViewHolder>(){

    private var listRosary = ArrayList<Rosary>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosaryViewHolder {
        val binding = ItemSlideContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RosaryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listRosary.size
    }

    override fun onBindViewHolder(holder: RosaryViewHolder, position: Int) {
        val rosary = listRosary[position]
        holder.bind(rosary)
        if (position == listRosary.size-1){
            viewPager2.post(runnable)
        }
    }

    private val runnable = Runnable {
        listRosary.addAll(listRosary)
        notifyDataSetChanged()
    }


    fun updateListRosary(newListRosary: ArrayList<Rosary>) {
        listRosary = newListRosary
        notifyDataSetChanged()
    }


    inner class RosaryViewHolder(val binding: ItemSlideContainerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rosary: Rosary){
            binding.tvRosaryName.text = rosary.rosaryName
            binding.tvRosaryNum.text = rosary.rosaryNum.toString()
        }
    }

}