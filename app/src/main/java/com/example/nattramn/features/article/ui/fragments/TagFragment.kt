package com.example.nattramn.features.article.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.core.commonAdapters.VerticalArticleAdapter
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.utils.snackMaker
import com.example.nattramn.databinding.FragmentTagBinding
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.OnArticleListener
import com.example.nattramn.features.article.ui.viewmodels.TagViewModel

class TagFragment : Fragment(),
    OnArticleListener {

    private lateinit var binding: FragmentTagBinding
    private lateinit var tagViewModel: TagViewModel
    private lateinit var tagAdapter: VerticalArticleAdapter
    private val args: TagFragmentArgs by navArgs()
    private lateinit var tagArg: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tagArg = args.tag

        binding = FragmentTagBinding.inflate(
            inflater, container, false
        ).apply {
            tagToolbarTitle.text = tagArg
        }

        binding.lifecycleOwner = viewLifecycleOwner
        tagViewModel = ViewModelProvider(this).get(TagViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButtonClick()

        setRecyclers()
    }

    private fun setRecyclers() {
        initRecyclerWithDatabase()
        sendTagArticlesRequest()
        observeNetResponse()
    }

    private fun initRecyclerWithDatabase() {
        showArticlesRecycler(tagViewModel.getTagArticlesDb(tagArg))
    }

    private fun sendTagArticlesRequest() {
        tagViewModel.getTagArticles(tagArg)
    }

    private fun observeNetResponse() {
        tagViewModel.tagArticlesResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS) {
                resource.data?.let { articles ->
                    showArticlesRecycler(articles)
                }
            } else if (resource.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    private fun showArticlesRecycler(articles: List<ArticleView>) {
        tagAdapter = VerticalArticleAdapter(
            articles,
            this
        )

        binding.recyclerTagArticles.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setBackButtonClick() {
        binding.tagRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun openProfile(username: String) {
        Navigation.findNavController(requireView())
            .navigate(
                TagFragmentDirections.actionTagFragmentToProfileFragment(username)
            )
    }

    private fun openArticle(slug: String) {
        Navigation.findNavController(requireView())
            .navigate(
                TagFragmentDirections.actionTagFragmentToArticleFragment(
                    tagViewModel.getSingleArticleDb(slug)
                )
            )
    }

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
        tagViewModel.bookmarkArticle(slug)

        tagViewModel.bookmarkResult.observe(viewLifecycleOwner, Observer { result ->

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