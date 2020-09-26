package com.example.nattramn.features.home.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nattramn.core.commonAdapters.HorizontalArticleAdapter
import com.example.nattramn.core.commonAdapters.VerticalArticleAdapter
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.utils.Constants
import com.example.nattramn.core.utils.snackMaker
import com.example.nattramn.databinding.FragmentForYouBinding
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.OnArticleListener
import com.example.nattramn.features.home.ui.viewmodels.HomeViewModel
import com.example.nattramn.features.user.data.AuthLocalDataSource

class ForYouFragment : Fragment(), OnArticleListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentForYouBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var feedArticlesAdapter: VerticalArticleAdapter
    private lateinit var topArticlesAdapter: HorizontalArticleAdapter

    private lateinit var feedArticles: MutableList<ArticleView>
    private lateinit var latestArticles: MutableList<ArticleView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        feedArticles = homeViewModel.setFeedArticlesDb()
        latestArticles = homeViewModel.setLatestArticlesDb()

        AuthLocalDataSource().getUsername()?.let { username ->
            homeViewModel.saveUserInfo(username)
        }

        binding = FragmentForYouBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeLayout.setOnRefreshListener(this)
        setRecyclers()
    }

    private fun setRecyclers() {
        initRecyclersWithDatabase()
        sendNetworkRequests()
        observeNetResponses()
    }

    private fun initRecyclersWithDatabase() {
        showFeedRecycler(homeViewModel.setFeedArticlesDb())
        showLatestRecycler(homeViewModel.setLatestArticlesDb())
    }

    private fun sendNetworkRequests() {
        homeViewModel.setLatestArticles()
        homeViewModel.setFeedArticles()
    }

    private fun observeNetResponses() {
        homeViewModel.feedResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS && !resource.data.isNullOrEmpty()) {
                showFeedRecycler(resource.data)
                feedArticles = resource.data.toMutableList()
            } else if (resource.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در دریافت مقالات برای شما")
            }
        })

        homeViewModel.latestArticlesResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS) {
                resource.data?.let {
                    latestArticles = it.toMutableList()
                    topArticlesAdapter =
                        HorizontalArticleAdapter(
                            resource.data,
                            this
                        )

                    topArticlesAdapter.notifyDataSetChanged()

                    binding.recyclerHomeTopArticles.apply {
                        adapter = topArticlesAdapter
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    }
                    binding.forYouLatestArticlesProgress.visibility = View.GONE
                }
            } else if (resource.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    private fun showLatestRecycler(articles: List<ArticleView>) {
        topArticlesAdapter =
            HorizontalArticleAdapter(
                articles,
                this
            )

        binding.recyclerHomeTopArticles.apply {
            adapter = topArticlesAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.forYouLatestArticlesProgress.visibility = View.GONE
    }

    private fun showFeedRecycler(articles: List<ArticleView>) {
        feedArticlesAdapter =
            VerticalArticleAdapter(
                articles,
                this@ForYouFragment
            )

        binding.recyclerHomeArticle.apply {
            adapter = feedArticlesAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        binding.forYouFeedProgress.visibility = View.GONE
        binding.textInputSearch.visibility = View.VISIBLE
    }

    private fun openArticle(slug: String) {
        Navigation.findNavController(requireView())
            .navigate(
                HomeFragmentDirections.actionHomeFragmentToArticleFragment(
                    homeViewModel.getSingleArticleDb(slug)
                )
            )
    }

    private fun openProfile(username: String) {
        Navigation.findNavController(requireView())
            .navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(username)
            )
    }

    /*      INTERFACES IMPLEMENTATION      */
    override fun onCardClick(slug: String) {
        openArticle(slug)
    }

    override fun onArticleTitleClick(slug: String) {
        openArticle(slug)
    }

    override fun onArticleSaveClick(
        slug: String, isBookmarked: Boolean, position: Int,
        item: String
    ) {
        if (isBookmarked) {
            homeViewModel.removeFromBookmarks(slug)
        } else {
            homeViewModel.bookmarkArticle(slug)
        }

        homeViewModel.bookmarkResult.observe(viewLifecycleOwner, Observer { result ->
            if (result.status == Status.SUCCESS) {
                if (item == Constants.FEED_OR_TAG) {
                    feedArticles[position].bookmarked = true
                    feedArticlesAdapter.notifyItemChanged(position)
                } else if (item == Constants.LATEST) {
                    latestArticles[position].bookmarked = true
                    topArticlesAdapter.notifyItemChanged(position)
                }
                snackMaker(requireView(), "این مقاله به لیست علاقه مندی ها اضافه شد")
            } else if (result.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })

        homeViewModel.removeBookmark.observe(viewLifecycleOwner, Observer { result ->
            if (result.status == Status.SUCCESS) {
                if (item == Constants.FEED_OR_TAG) {
                    feedArticles[position].bookmarked = false
                    feedArticlesAdapter.notifyItemChanged(position)
                } else if (item == Constants.LATEST) {
                    latestArticles[position].bookmarked = false
                    topArticlesAdapter.notifyItemChanged(position)
                }
                snackMaker(requireView(), "این مقاله از لیست علاقه مندی ها حذف شد")
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

    override fun onRefresh() {
        Toast.makeText(requireContext(), "refresh", Toast.LENGTH_SHORT).show()
        sendNetworkRequests()
        binding.swipeLayout.isRefreshing = false
    }

}