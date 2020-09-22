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
import com.example.nattramn.core.VerticalArticleAdapter
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.snackMaker
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
        homeViewModel.setLatestArticlesDb().observe(viewLifecycleOwner, Observer {
            topArticlesAdapter =
                HorizontalArticleAdapter(
                    it,
                    this
                )

            binding.recyclerHomeTopArticles.apply {
                adapter = topArticlesAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            binding.forYouLatestArticlesProgress.visibility = View.GONE
        })
        homeViewModel.setLatestArticles()
        homeViewModel.setFeedArticles()
    }

    private fun setContent() {
        homeViewModel.feedResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS && !resource.data.isNullOrEmpty()) {

                println("jalil size all: ${resource.data.size}")

                feedArticlesAdapter =
                    VerticalArticleAdapter(
                        resource.data,
                        this@ForYouFragment
                    )

                binding.recyclerHomeArticle.apply {
                    adapter = feedArticlesAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                }

                binding.forYouFeedProgress.visibility = View.GONE
                binding.textInputSearch.visibility = View.VISIBLE

            } else {
                println("jalil error ${resource.message}")
            }
        })

        /*homeViewModel.latestArticlesResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS && !resource.data.isNullOrEmpty()) {

                println("jalil size all: ${resource.data.size}")

                topArticlesAdapter =
                    HorizontalArticleAdapter(
                        resource.data,
                        this
                    )

                binding.recyclerHomeTopArticles.apply {
                    adapter = topArticlesAdapter
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }

                binding.forYouLatestArticlesProgress.visibility = View.GONE
            }
        })*/
    }

    private fun onArticleClick(slug: String) {

        homeViewModel.getSingleArticle(slug)

        homeViewModel.singleArticleResult.observe(viewLifecycleOwner, Observer { resourceArticle ->
            if (resourceArticle.status == Status.SUCCESS) {

                resourceArticle.data?.let { articleView ->
                    Navigation.findNavController(requireView())
                        .navigate(
                            HomeFragmentDirections.actionHomeFragmentToArticleFragment(
                                articleView
                            )
                        )
                }
            } else if (resourceArticle.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })

    }

    private fun openProfile(username: String) {
        Navigation.findNavController(requireView())
            .navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(username)
            )
    }

    override fun onCardClick(slug: String) {
        onArticleClick(slug)
    }

    override fun onArticleTitleClick(slug: String) {
        onArticleClick(slug)
    }

    override fun onArticleSaveClick(slug: String) {
        homeViewModel.bookmarkArticle(slug)

        homeViewModel.bookmarkResult.observe(viewLifecycleOwner, Observer { result ->

            if (result.status == Status.SUCCESS) {
                snackMaker(requireView(), "این مقاله به لیست علاقه مندی ها اضافه شد")
            } else if (result.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    override fun onAuthorNameClick(username: String) {
        openProfile(username)
    }

    override fun onAuthorIconClick(username: String) {
        openProfile(username)
    }

}