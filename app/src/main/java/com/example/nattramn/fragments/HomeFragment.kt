package com.example.nattramn.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.Utils
import com.example.nattramn.adapters.HorizontalArticleAdapter
import com.example.nattramn.adapters.VerticalArticleAdapter
import com.example.nattramn.databinding.FragmentHomeBinding
import com.example.nattramn.recyclerItemListeners.OnArticleListener
import com.example.nattramn.viewModels.HomeViewModel
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

class HomeFragment : Fragment(), OnArticleListener {

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
        snapHorizontal.attachToRecyclerView(binding.recyclerHomeTopArticles)

        setOnProfileClicked()

        setOnWriteClicked()

        setRecyclers()

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
                        ).user
                    )
                )
        }

    }

    private fun setRecyclers() {

        observeRecyclersContent()

        feedArticlesAdapter =
            VerticalArticleAdapter(Utils(requireContext()).initArticles(), this)
        topArticlesAdapter =
            HorizontalArticleAdapter(Utils(requireContext()).initArticles(), this)

        /*These two lines of codes trigger content observers*/
        homeViewModel.setFeedArticles()
        homeViewModel.setTopArticles()

    }

    private fun observeRecyclersContent() {
        homeViewModel.feedArticles.observe(viewLifecycleOwner, Observer {

            feedArticlesAdapter.articles = it

            binding.recyclerHomeArticle.apply {
                adapter = feedArticlesAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        })

        homeViewModel.topArticles.observe(viewLifecycleOwner, Observer {

            topArticlesAdapter.articles = it

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
                    ).user
                )
            )
    }

    override fun onAuthorIconClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                    Utils(
                        requireContext()
                    ).user
                )
            )
    }

    override fun onArticleTitleClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
    }

}