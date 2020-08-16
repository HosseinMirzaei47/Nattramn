package com.example.nattramn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setRecyclers()

    }

    private fun setRecyclers() {
        val verticalAdapter = VerticalArticleAdapter(this)
        recyclerHomeArticle.layoutManager = LinearLayoutManager(this)
        recyclerHomeArticle.adapter = verticalAdapter

        val horizontalAdapter = HorizontalArticleAdapter(this)
        recyclerHomeTopArticles.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerHomeTopArticles.adapter = horizontalAdapter
    }

}