package com.example.nattramn

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.adapters.HorizontalArticleAdapter
import com.example.nattramn.adapters.VerticalArticleAdapter
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
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

        /*Home articles vertical RecyclerView*/
        val snapVertical = GravitySnapHelper(Gravity.TOP)
        snapVertical.attachToRecyclerView(recyclerHomeArticle)

        val verticalAdapter = VerticalArticleAdapter(this)
        recyclerHomeArticle.apply {
            adapter = verticalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        /*Home top articles horizontal RecyclerView*/
        val snapHorizontal = GravitySnapHelper(Gravity.CENTER)
        snapHorizontal.attachToRecyclerView(recyclerHomeTopArticles)

        val horizontalAdapter = HorizontalArticleAdapter(this)
        recyclerHomeTopArticles.apply {
            adapter = horizontalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}