package com.example.myislamicapp.ui.quran.indexAndJozza.jozzaList.soraList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myislamicapp.R
import com.example.myislamicapp.data.model.quran.Sora
import com.example.myislamicapp.databinding.ItemIndexListBinding


class IndexAdapter(val context: Context, val soraClickListener: SoraClickListener) :
    Adapter<IndexAdapter.SoraViewHolder>() {

    private var listSora = ArrayList<Sora>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoraViewHolder {
        val binding = ItemIndexListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SoraViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listSora.size
    }

    override fun onBindViewHolder(holder: SoraViewHolder, position: Int) {
        val sora = listSora[position]

        holder.bind(sora)

        holder.itemView.setOnClickListener {
            soraClickListener.onItemClick(sora)
        }
    }

    fun updateListSora(listSoraNews: ArrayList<Sora>) {
        listSora = listSoraNews
        notifyDataSetChanged()
    }


    inner class SoraViewHolder(val binding: ItemIndexListBinding) : ViewHolder(binding.root) {

        fun bind(sora: Sora) {
            binding.apply {
                tvStartSoraNum.text = sora.startPage.toString()
                tvAyatNum.text = sora.ayatNum.toString()
                tvSoraName.text = sora.arabicName
                tvSoraNum.text = sora.soraNumber.toString()

                if (sora.soraType == "city") {
                    ivSoraDescent.setImageResource(R.drawable.dome)
                } else {
                    ivSoraDescent.setImageResource(R.drawable.kaaba)
                }
            }

        }
    }


    interface SoraClickListener {
        fun onItemClick(sora: Sora)
    }

}