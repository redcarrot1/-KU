package com.example.volunteerku.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.R
import com.example.volunteerku.databinding.ActivityMainBinding
import com.example.volunteerku.fragment.MyViewPagerAdapter
import com.example.volunteerku.fragment.MypageActivity
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val textarr = arrayListOf<String>("게시판", "검색하기", "내정보")
    val imgarr = arrayListOf<Int>(
        R.drawable.billboard_icon_135884, R.drawable.baseline_search_24, R.drawable.baseline_bookmark_24
    )
    lateinit var adapter: MyViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        adapter = MyViewPagerAdapter(this)
        binding.viewpager.adapter = adapter
        TabLayoutMediator(binding.tablayout, binding.viewpager) { tab, pos ->
            tab.text = textarr[pos]
            tab.setIcon(imgarr[pos])
        }.attach()
    }

    override fun onBackPressed() {
        println("currItem ${binding.viewpager.childCount}")
        if (binding.viewpager.currentItem == 1) {
            val child = supportFragmentManager.fragments.find { it.isResumed } as? MypageActivity
            child?.let {
                if (it.childFragmentManager.backStackEntryCount >= 1) {
                    it.childFragmentManager.popBackStackImmediate()
                } else {
                    super.onBackPressed()
                }
            }
        } else {
            super.onBackPressed()
        }
    }
}
