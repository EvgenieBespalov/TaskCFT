package com.example.cft1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.cft1.Adapters.FragmentsAdapter

class MainActivity : AppCompatActivity(){
    private lateinit var adapter: FragmentsAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = FragmentsAdapter(this)

        viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter
    }
}
