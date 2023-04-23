package com.example.myislamicapp.ui.azkar.azkarType

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myislamicapp.data.model.azkar.ZekrType
import com.example.myislamicapp.databinding.ItemAzkarTypeListBinding


class AzkarTypeAdapter(
    val context: Context,
    val zekrClickListener: ZekrClickListener
) : Adapter<AzkarTypeAdapter.AzkarTypeViewHolder>() {

    private var listZekrType = ArrayList<com.example.myislamicapp.data.model.azkar.ZekrType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarTypeViewHolder {
        val binding =
            ItemAzkarTypeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AzkarTypeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listZekrType.size
    }

    override fun onBindViewHolder(holder: AzkarTypeViewHolder, position: Int) {
        val zekrType = listZekrType[position]

        holder.bind(zekrType)

        holder.itemView.setOnClickListener {
            zekrClickListener.onItemClick(zekrType)
        }
    }

    fun updateListZekrType(listZekrTypeNew: MutableSet<com.example.myislamicapp.data.model.azkar.ZekrType>) {
        listZekrType = ArrayList(listZekrTypeNew)
        notifyDataSetChanged()
    }


    inner class AzkarTypeViewHolder(val binding: ItemAzkarTypeListBinding) :
        ViewHolder(binding.root) {

        fun bind(zekrType: com.example.myislamicapp.data.model.azkar.ZekrType) {
            if(zekrType.zekrName != "الدعاء حينما يقع ما لا يرضاه أو غلب على أمره"){
                binding.tvZekrName.text = zekrType.zekrName
            }
        }
    }


    interface ZekrClickListener {
        fun onItemClick(zekrType: com.example.myislamicapp.data.model.azkar.ZekrType)
    }
}