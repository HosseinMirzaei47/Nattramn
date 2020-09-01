package com.example.nattramn.features.home.ui.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.nattramn.R
import com.example.nattramn.core.HorizontalArticleAdapter
import com.example.nattramn.core.Utils
import com.example.nattramn.core.VerticalArticleAdapter
import com.example.nattramn.core.ViewPagerAdapter
import com.example.nattramn.databinding.FragmentHomeBinding
import com.example.nattramn.features.home.ui.viewmodels.HomeViewModel
import com.example.nattramn.fragments.pager.ForYouFragment
import com.example.nattramn.fragments.pager.KeyWordFragment
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var feedArticlesAdapter: VerticalArticleAdapter
    private lateinit var topArticlesAdapter: HorizontalArticleAdapter

    private val snapHorizontal = GravitySnapHelper(Gravity.CENTER)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //snapHorizontal.attachToRecyclerView(binding.recyclerHomeTopArticles)

        setOnProfileClicked()

        setOnWriteClicked()

        // setRecyclers()

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ForYouFragment(), resources.getString(R.string.HomeForYou))
        adapter.addFragment(KeyWordFragment(), resources.getString(R.string.HomeTabTitleKeywords))

        binding.viewPager.adapter = adapter
        binding.homeTabLayout.setupWithViewPager(binding.viewPager)

    }

    private fun setOnWriteClicked() {

        binding.homeWriteButton.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(HomeFragmentDirections.actionHomeFragmentToWriteFragment())
        }

    }

    private fun setOnProfileClicked() {

        binding.articleProfileIcon.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(
                    HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                        Utils(
                            requireContext()
                        ).userView
                    )
                )
        }

    }

    /*private fun setRecyclers() {

        observeRecyclersContent()

        homeViewModel.setFeedArticles()
        homeViewModel.setTopArticles()

        feedArticlesAdapter =
            VerticalArticleAdapter(
                homeViewModel.feedArticles.value!!,
                this
            )
        topArticlesAdapter =
            HorizontalArticleAdapter(
                homeViewModel.topArticles.value!!,
                this
            )

    }

    private fun observeRecyclersContent() {
        homeViewModel.feedArticles.observe(viewLifecycleOwner, Observer {

            feedArticlesAdapter.articleViews = it

            binding.recyclerHomeArticle.apply {
                adapter = feedArticlesAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        })

        homeViewModel.topArticles.observe(viewLifecycleOwner, Observer {

            topArticlesAdapter.articleViews = it

            binding.recyclerHomeTopArticles.apply {
                adapter = topArticlesAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        })
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
    }*/

}