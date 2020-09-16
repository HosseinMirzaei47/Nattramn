package com.example.nattramn.features.home.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.core.HorizontalArticleAdapter
import com.example.nattramn.core.Utils
import com.example.nattramn.core.VerticalArticleAdapter
import com.example.nattramn.core.resource.Status
import com.example.nattramn.databinding.FragmentForYouBinding
import com.example.nattramn.features.article.ui.OnArticleListener
import com.example.nattramn.features.home.ui.viewmodels.HomeViewModel

class ForYouFragment : Fragment(), OnArticleListener {

    private lateinit var binding: FragmentForYouBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var feedArticlesAdapter: VerticalArticleAdapter
    private lateinit var topArticlesAdapter: HorizontalArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentForYouBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclers()
    }

    private fun setRecyclers() {
        setContent()
        homeViewModel.setFeedArticles()
        homeViewModel.setTopArticles()
    }

    private fun setContent() {
        binding.forYouProgress.visibility = View.VISIBLE

        homeViewModel.feedResult.observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS) {
                println("jalil size ${it.data?.size}")

                it.data?.let { articlesList ->
                    feedArticlesAdapter =
                        VerticalArticleAdapter(
                            articlesList,
                            this@ForYouFragment
                        )
                }

                binding.recyclerHomeArticle.apply {
                    adapter = feedArticlesAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }

                binding.forYouProgress.visibility = View.INVISIBLE
                binding.textInputSearch.visibility = View.VISIBLE

                /*it.data?.map { article ->
                    println("-- ${article.commentViews}")
                    article.commentViews.size.apply {
                        println("jalil count size: $this")
                    }
                }?.sum()*/

            } else {
                println("jalil error ${it.message}")
            }
        })

        homeViewModel.topArticles.observe(viewLifecycleOwner, Observer {
            topArticlesAdapter =
                HorizontalArticleAdapter(
                    it,
                    this@ForYouFragment
                )

            binding.recyclerHomeTopArticles.apply {
                adapter = topArticlesAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        })
    }

    private fun onArticleClick(slug: String) {

        homeViewModel.getSingleArticle(slug)

        homeViewModel.singleArticleResult.observe(viewLifecycleOwner, Observer { resourceArticle ->
            if (resourceArticle.status == Status.SUCCESS) {

                println("jalil vari ${resourceArticle.data}")

                Navigation.findNavController(requireView())
                    .navigate(
                        HomeFragmentDirections.actionHomeFragmentToArticleFragment(
                            resourceArticle.data!!
                        )
                    )
            }
        })

    }

    override fun onCardClick(slug: String) {
        onArticleClick(slug)
    }

    override fun onArticleTitleClick(slug: String) {
        onArticleClick(slug)
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

}