package com.example.nattramn.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.adapters.HorizontalArticleAdapter
import com.example.nattramn.adapters.VerticalArticleAdapter
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnProfileClicked()

        setOnWriteClicked()

        setRecyclers()

    }

    private fun setOnWriteClicked() {

        homeWriteButton.setOnClickListener {
            val fragmentArticle = ArticleFragment()

            fragmentManager?.beginTransaction()
                ?.replace(R.id.homeFrame, fragmentArticle)
                ?.addToBackStack(null)
                ?.commit()
        }

    }

    private fun setOnProfileClicked() {

        articleProfileIcon.setOnClickListener {

            val profileFragment = ProfileFragment()

            fragmentManager?.beginTransaction()
                ?.replace(R.id.homeFrame, profileFragment)
                ?.addToBackStack(null)
                ?.commit()

        }

    }

    private fun setRecyclers() {

        /*Home articles vertical RecyclerView*/
        val snapVertical = GravitySnapHelper(Gravity.TOP)
        snapVertical.attachToRecyclerView(recyclerHomeArticle)

        val verticalAdapter = VerticalArticleAdapter(requireContext())
        recyclerHomeArticle.apply {
            adapter = verticalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        /*Home top articles horizontal RecyclerView*/
        val snapHorizontal = GravitySnapHelper(Gravity.CENTER)
        snapHorizontal.attachToRecyclerView(recyclerHomeTopArticles)

        val horizontalAdapter = HorizontalArticleAdapter(requireContext())
        recyclerHomeTopArticles.apply {
            adapter = horizontalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}