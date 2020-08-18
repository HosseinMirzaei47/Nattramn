package com.example.nattramn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.adapters.HorizontalArticleAdapter
import com.example.nattramn.adapters.VerticalArticleAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setOnProfileClicked()

        setOnWriteClicked()

        setRecyclers()

    }

    private fun setOnWriteClicked() {

        homeWriteButton.setOnClickListener {
            val fragmentArticle = FragmentArticle()

            supportFragmentManager.beginTransaction()
                .replace(R.id.homeFrame, fragmentArticle)
                .addToBackStack(null)
                .commit()
        }

    }

    private fun setOnProfileClicked() {

        articleProfileIcon.setOnClickListener {

            val profileFragment = ProfileFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.homeFrame, profileFragment)
                .addToBackStack(null)
                .commit()

        }

    }

    private fun setRecyclers() {

        val verticalAdapter = VerticalArticleAdapter(this)
        recyclerHomeArticle.apply {
            adapter = verticalAdapter
            layoutManager = LinearLayoutManager(context)
        }

        val horizontalAdapter = HorizontalArticleAdapter(this)
        recyclerHomeTopArticles.apply {
            adapter = horizontalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}