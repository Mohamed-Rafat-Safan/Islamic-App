package com.example.myislamicapp.ui.azkar.azkarContent

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myislamicapp.R
import com.example.myislamicapp.data.model.azkar.Zekr
import com.example.myislamicapp.databinding.ItemZekrContentListBinding


class AzkarContentAdapter(
    val context: Context
) : RecyclerView.Adapter<AzkarContentAdapter.AzkarContentViewHolder>() {

    private var listZekrContent = ArrayList<Zekr>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AzkarContentViewHolder {
        val binding =
            ItemZekrContentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AzkarContentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listZekrContent.size
    }

    override fun onBindViewHolder(holder: AzkarContentViewHolder, position: Int) {
        val zekr = listZekrContent[position]

        holder.bind(zekr)

        holder.itemView.setOnClickListener {
            val pb = it.findViewById<ProgressBar>(R.id.progressBar_count)
            val tv = it.findViewById<TextView>(R.id.tv_changeNumProgress)


            if (listZekrContent[position].currentCount < listZekrContent[position].count.toInt()) {
                listZekrContent[position].currentCount += 1

                pb.max = listZekrContent[position].count.toInt()
                pb.progress = listZekrContent[position].currentCount
                tv.text = listZekrContent[position].currentCount.toString()

                playClickSound()
            }

        }
    }

    private fun playClickSound() {
        var mediaPlayer = MediaPlayer()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            mediaPlayer.stop()
            mediaPlayer.seekTo(0)
        }
        mediaPlayer = MediaPlayer.create(context, R.raw.click)
        mediaPlayer.start()
    }

    fun updateListZekrContent(listZekrContentNew: ArrayList<Zekr>) {
        listZekrContent = listZekrContentNew
        notifyDataSetChanged()
    }


    inner class AzkarContentViewHolder(val binding: ItemZekrContentListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(zekr: Zekr) {
            binding.progressBarCount.progress = 0
            binding.tvTotalZekr.text = listZekrContent.size.toString()
            binding.tvChangeNumZekr.text = zekr.zekrNum.toString()
            binding.tvZekrContent.text = zekr.zekr
            binding.tvDescription.text = zekr.description

            binding.progressBarCount.max = zekr.count.toInt()
            binding.progressBarCount.progress = zekr.currentCount

            binding.tvTotalProgress.text = zekr.count
            binding.tvChangeNumProgress.text = zekr.currentCount.toString()

        }
    }

}