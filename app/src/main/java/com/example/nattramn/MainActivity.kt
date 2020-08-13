package com.example.nattramn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val adapter = HorizontalArticleAdapter(this)
        recyclerHomeArticle.layoutManager = LinearLayoutManager(this)
        recyclerHomeArticle.adapter = adapter

    }
}