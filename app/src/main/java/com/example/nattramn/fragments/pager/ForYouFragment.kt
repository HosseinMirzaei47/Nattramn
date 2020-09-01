package com.example.nattramn.fragments.pager

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.core.HorizontalArticleAdapter
import com.example.nattramn.core.Utils
import com.example.nattramn.core.VerticalArticleAdapter
import com.example.nattramn.features.article.ui.OnArticleListener
import com.example.nattramn.features.home.ui.fragments.HomeFragmentDirections
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.fragment_for_you.*

class ForYouFragment : Fragment(), OnArticleListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_for_you, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclers()
    }

    private fun setRecyclers() {

        val verticalAdapter = VerticalArticleAdapter(Utils(requireContext()).initArticles(), this)
        recyclerHomeArticle.apply {
            adapter = verticalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        val snapHorizontal = GravitySnapHelper(Gravity.CENTER)
        snapHorizontal.attachToRecyclerView(recyclerHomeTopArticles)

        val horizontalAdapter =
            HorizontalArticleAdapter(Utils(requireContext()).initArticles(), this)
        recyclerHomeTopArticles.apply {
            adapter = horizontalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCardClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
    }

    override fun onArticleSaveClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(HomeFragmentDirections.actionHomeFragmentToTagFragment())
    }

    override fun onAuthorNameClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                    Utils(
                        requireContext()
                    ).userView
                )
            )
    }

    override fun onAuthorIconClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                    Utils(
                        requireContext()
                    ).userView
                )
            )
    }

    override fun onArticleTitleClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
    }

}