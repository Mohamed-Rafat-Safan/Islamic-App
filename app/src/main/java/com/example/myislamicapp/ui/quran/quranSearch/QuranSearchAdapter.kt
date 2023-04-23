package com.example.myislamicapp.ui.quran.quranSearch

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myislamicapp.data.model.quran.Aya
import com.example.myislamicapp.databinding.ItemQuranSearchResultBinding


class QuranSearchAdapter(
    val context: Context,
    val searchResultClickListener: SearchResultClickListener
) :
    RecyclerView.Adapter<QuranSearchAdapter.QuranSearchViewHolder>() {

    private var listSearchResult = ArrayList<com.example.myislamicapp.data.model.quran.Aya>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranSearchViewHolder {
        val binding =
            ItemQuranSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuranSearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listSearchResult.size
    }

    override fun onBindViewHolder(holder: QuranSearchViewHolder, position: Int) {
        val aya = listSearchResult[position]

        holder.bind(aya)

        holder.itemView.setOnClickListener {
            searchResultClickListener.onItemClick(aya)
        }
    }

    fun updateListSearchResult(listSoraNews: ArrayList<com.example.myislamicapp.data.model.quran.Aya>) {
        listSearchResult = listSoraNews
        notifyDataSetChanged()
    }


    inner class QuranSearchViewHolder(val binding: ItemQuranSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(aya: com.example.myislamicapp.data.model.quran.Aya) {
            binding.apply {
                tvSoraNum.text = aya.sora.toString()
                tvSoraName.text = aya.sora_name_ar
                tvAyaNum.text = aya.aya_no.toString()
                tvAyaContent.text = aya.aya_text
            }
        }
    }


    interface SearchResultClickListener {
        fun onItemClick(aya: com.example.myislamicapp.data.model.quran.Aya)
    }
}