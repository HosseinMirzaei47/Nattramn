package com.example.nattramn.features.article.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.core.VerticalArticleAdapter
import com.example.nattramn.core.resource.Status
import com.example.nattramn.databinding.FragmentTagBinding
import com.example.nattramn.features.article.ui.OnArticleListener
import com.example.nattramn.features.article.ui.viewmodels.TagViewModel
import com.google.android.material.snackbar.Snackbar

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

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tag, container, false
        )

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

        tagViewModel.getTagArticles(tagArg)

        tagViewModel.tagArticlesResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS) {

                resource.data?.let { articles ->
                    tagAdapter = VerticalArticleAdapter(
                        articles,
                        this
                    )

                    binding.recyclerTagArticles.apply {
                        adapter = tagAdapter
                        layoutManager = LinearLayoutManager(context)
                    }
                }

            } else if (resource.status == Status.ERROR) {
                Snackbar.make(requireView(), "خطا در ارتباط با سرور", Snackbar.LENGTH_SHORT).show()
            }
        })

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
        tagViewModel.getSingleArticle(slug)

        tagViewModel.singleArticleResult.observe(viewLifecycleOwner, Observer { resourceArticle ->
            if (resourceArticle.status == Status.SUCCESS) {

                resourceArticle.data?.let { articleView ->
                    Navigation.findNavController(requireView())
                        .navigate(
                            TagFragmentDirections.actionTagFragmentToArticleFragment(
                                articleView
                            )
                        )
                }
            } else if (resourceArticle.status == Status.ERROR) {
                Snackbar.make(requireView(), "خطا در ارتباط با سرور", Snackbar.LENGTH_SHORT)
            }
        })
    }

    override fun onCardClick(slug: String) {
        openArticle(slug)
    }

    override fun onArticleTitleClick(slug: String) {
        openArticle(slug)
    }

    override fun onArticleSaveClick(slug: String) {

        tagViewModel.bookmarkArticle(slug)

        tagViewModel.bookmarkResult.observe(viewLifecycleOwner, Observer { result ->

            if (result.status == Status.SUCCESS) {
                Snackbar.make(
                    requireView(), "این مقاله به لیست علاقه مندی ها اضافه شد", Snackbar.LENGTH_LONG
                ).show()
            } else {
                Snackbar.make(
                    requireView(), "خطا در ارتباط با سرور", Snackbar.LENGTH_LONG
                ).show()
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
