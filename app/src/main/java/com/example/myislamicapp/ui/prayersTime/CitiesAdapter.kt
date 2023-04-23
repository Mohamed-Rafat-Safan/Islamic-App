package com.example.myislamicapp.ui.prayersTime

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myislamicapp.data.model.prayers.City
import com.example.myislamicapp.databinding.ItemCitiesNameBinding


class CitiesAdapter(context: Context, val cityClickListener: CityClickListener) :
    Adapter<CitiesAdapter.CitiesViewHolder>() {

    var listCities = ArrayList<City>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val binding =
            ItemCitiesNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitiesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCities.size
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val city = listCities[position]

        holder.bind(city)

        holder.itemView.setOnClickListener {
            cityClickListener.onItemClick(city)
        }
    }

    fun updateListCities(newListCities: ArrayList<City>) {
        listCities = newListCities
        notifyDataSetChanged()
    }

    inner class CitiesViewHolder(val binding: ItemCitiesNameBinding) : ViewHolder(binding.root) {

        fun bind(city: City) {
            binding.tvCityName.text = city.city_name_ar
        }
    }


    interface CityClickListener {
        fun onItemClick(city: City)
    }

}