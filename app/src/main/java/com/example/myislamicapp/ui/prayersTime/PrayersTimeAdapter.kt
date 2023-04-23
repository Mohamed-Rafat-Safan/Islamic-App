package com.example.myislamicapp.ui.prayersTime

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myislamicapp.data.model.prayers.PrayersTiming
import com.example.myislamicapp.databinding.ItemPrayersTimeBinding

class PrayersTimeAdapter(context: Context, val prayerClickListener: PrayerClickListener) :
    RecyclerView.Adapter<PrayersTimeAdapter.PrayersViewHolder>() {

    var listPrayers = ArrayList<PrayersTiming>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayersViewHolder {
        val binding =
            ItemPrayersTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrayersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPrayers.size
    }

    override fun onBindViewHolder(holder: PrayersViewHolder, position: Int) {
        val prayer = listPrayers[position]

        holder.bind(prayer)

        holder.itemView.setOnClickListener {
            prayerClickListener.onItemClick(prayer)
        }
    }

    fun updateListPrayers(newListPrayers: ArrayList<PrayersTiming>) {
        listPrayers = newListPrayers
        notifyDataSetChanged()
    }

    inner class PrayersViewHolder(val binding: ItemPrayersTimeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(prayer : PrayersTiming) {
            binding.tvPrayerName.text = prayer.prayersName
            binding.tvPrayersTime.text = prayer.prayersTime
            binding.tvTimeType.text = prayer.timeType
        }
    }

    interface PrayerClickListener {
        fun onItemClick(parTiming: PrayersTiming)
    }

}