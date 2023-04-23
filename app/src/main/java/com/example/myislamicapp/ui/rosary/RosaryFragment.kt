package com.example.myislamicapp.ui.rosary

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.myislamicapp.R
import com.example.myislamicapp.data.model.Rosary
import com.example.myislamicapp.databinding.FragmentRosaryBinding
import java.lang.Math.abs


class RosaryFragment : Fragment() {
    private lateinit var mNavController: NavController
    private var _binding: FragmentRosaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var rosariesList: ArrayList<Rosary>
    private lateinit var adapter: RosaryAdapter
    private var currentRosary = 0
    private var lastCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRosaryBinding.inflate(inflater, container, false)

        binding.ivBackRosary.setOnClickListener {
            mNavController.currentBackStackEntry?.let { backEntry -> mNavController.popBackStack(backEntry.destination.id,true) }
        }


        init()
        setUpTransformer()


        binding.progressBar.max = rosariesList[currentRosary].rosaryNum
        moveProgress(lastCount, rosariesList[currentRosary].rosaryNum)

        binding.btnProgressCount.setOnClickListener {
            if (lastCount < binding.progressBar.max) {
                lastCount += 1
                moveProgress(lastCount, binding.progressBar.max)
                rosariesList[currentRosary].lastCount = lastCount
                playClickSound()
            }
        }



        binding.vpRosaries.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                adapter.updateListRosary(rosariesList)
                currentRosary = position
                val data = rosariesList[position]
                binding.progressBar.max = data.rosaryNum
                lastCount = data.lastCount
                moveProgress(data.lastCount, binding.progressBar.max)
            }
        })


        return binding.root
    }


    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        binding.vpRosaries.setPageTransformer(transformer)
    }


    private fun init() {
        rosariesList = ArrayList()

        rosariesList.add(Rosary("سُبْحَانَ اللَّهُ", 33))
        rosariesList.add(Rosary("الْحَمْدُ لِلَّهِ", 33))
        rosariesList.add(Rosary("اللهُ أَكْبَرُ", 33))
        rosariesList.add(Rosary("لَا إِلَهَ إِلَّا اللهُ", 100))
        rosariesList.add(Rosary("حَسْبِيَ اللَّهُ وَنِعْمَ الْوَكِيلُ", 100))
        rosariesList.add(Rosary("أَسْتَغْفِرُ اللَّهَ الْعَظِيمَ وَأَتوبُ إِلَيْكَ", 100))
        rosariesList.add(Rosary("لا حَوْلَ وَلا قُوَّةَ إِلا باللَّهِ", 100))
        rosariesList.add(Rosary("سُبْحَانَ اللَّهِ وَبِحَمْدِهِ سُبْحَانَ اللَّهِ الْعَظِيمِ", 100))

        adapter = RosaryAdapter(binding.vpRosaries)
        adapter.updateListRosary(rosariesList)

        binding.vpRosaries.adapter = adapter
        binding.vpRosaries.offscreenPageLimit = 3
        binding.vpRosaries.clipToPadding = false
        binding.vpRosaries.clipChildren = false
        binding.vpRosaries.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }


    fun moveProgress(count: Int, total: Int) {
        binding.progressBar.progress = count
        binding.btnProgressCount.text = "$count/$total"
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}