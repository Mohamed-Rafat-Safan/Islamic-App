package com.example.myislamicapp.ui.quran.indexAndJozza.listFragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myislamicapp.data.model.quran.Jozza
import com.example.myislamicapp.databinding.ItemJozzaListBinding


class JozzaAdapter (val context: Context, val jozzaClickListener: JozzaClickListener) :
    RecyclerView.Adapter<JozzaAdapter.JozzaViewHolder>() {

    private var listJozza = ArrayList<com.example.myislamicapp.data.model.quran.Jozza>()
    private val arabicNumber: Map<Int, String> = mapOf(
        Pair(1, "الجزء الأول"), Pair(2, "الجزء الثاني"), Pair(3, "الجزء الثالث"),
        Pair(4, "الجزء الرابع"), Pair(5, "الجزء الخامس"), Pair(6, "الجزء السادس"),
        Pair(7, "الجزء السابع"), Pair(8, "الجزء الثامن"), Pair(9, "الجزء التاسع"),
        Pair(10, "الجزء العاشر"), Pair(11, "الجزء الحادي عشر"), Pair(12, "الجزء الثاني عشر"),
        Pair(13, "الجزء الثالث عشر"), Pair(14, "الجزء الرابع عشر"), Pair(15, "الجزء الخامس عشر"),
        Pair(16, "الجزء السادس عشر"), Pair(17, "الجزء السابع عشر"), Pair(18, "الجزء الثامن عشر"),
        Pair(19, "الجزء التاسع عشر"), Pair(20, "الجزء العشرون"), Pair(21, "الجزء الحادي والعشرون"),
        Pair(22, "الجزء الثاني والعشرون"), Pair(23, "الجزء الثالث والعشرون"), Pair(24, "الجزء الرابع والعشرون"),
        Pair(25, "الجزء الخامس والعشرون"), Pair(26, "الجزء السادس والعشرون"), Pair(27, "الجزء السابع والعشرون"),
        Pair(28, "الجزء الثامن والعشرون"), Pair(29, "الجزء التاسع والعشرون"), Pair(30, "الجزء الثلاثون"),
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JozzaViewHolder {
        val binding = ItemJozzaListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JozzaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listJozza.size
    }

    override fun onBindViewHolder(holder: JozzaViewHolder, position: Int) {
        val jozza = listJozza[position]

        holder.bind(jozza)

        holder.itemView.setOnClickListener {
            jozzaClickListener.onItemClick(jozza)
        }
    }

    fun updateListJozza(listJozzaNews: ArrayList<com.example.myislamicapp.data.model.quran.Jozza>) {
        listJozza = listJozzaNews
        notifyDataSetChanged()
    }


    inner class JozzaViewHolder(val binding: ItemJozzaListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(jozza: com.example.myislamicapp.data.model.quran.Jozza) {
            binding.apply {
                tvIndex.text = jozza.jozzaNumber.toString()
                tvJozzaNum.text = arabicNumber[jozza.jozzaNumber]
                tvPageNum.text = jozza.startPage.toString()
            }
        }
    }


    interface JozzaClickListener {
        fun onItemClick(jozza: com.example.myislamicapp.data.model.quran.Jozza)
    }

}