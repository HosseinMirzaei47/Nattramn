package com.example.nattramn.features.article.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.core.Utils
import com.example.nattramn.core.VerticalArticleAdapter
import com.example.nattramn.databinding.FragmentTagBinding
import com.example.nattramn.features.article.ui.OnArticleListener
import com.example.nattramn.features.article.ui.viewmodels.TagViewModel

class TagFragment : Fragment(),
    OnArticleListener {

    private lateinit var binding: FragmentTagBinding
    private lateinit var tagViewModel: TagViewModel
    private lateinit var tagAdapter: VerticalArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        tagViewModel.setTagArticles()

        tagAdapter = VerticalArticleAdapter(
            tagViewModel.tagArticles.value!!,
            this
        )

        observeTagArticles()

    }

    private fun observeTagArticles() {
        tagViewModel.tagArticles.observe(viewLifecycleOwner, Observer {

            tagAdapter.articleViews = it

            binding.recyclerTagArticles.apply {
                adapter = tagAdapter
                layoutManager = LinearLayoutManager(context)
            }

        })
    }

    private fun setBackButtonClick() {
        binding.tagRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }

    override fun onCardClick(slug: String) {
        Navigation.findNavController(requireView())
            .navigate(TagFragmentDirections.actionTagFragmentToArticleFragment(Utils(requireContext()).initArticles()[0]))
    }

    override fun onArticleTitleClick(slug: String) {
        Navigation.findNavController(requireView())
            .navigate(TagFragmentDirections.actionTagFragmentToArticleFragment(Utils(requireContext()).initArticles()[0]))
    }

    override fun onArticleSaveClick(slug: String) {
        Toast.makeText(context, "Tag page. Article clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onAuthorNameClick(slug: String) {
        Navigation.findNavController(requireView())
            .navigate(
                TagFragmentDirections.actionTagFragmentToProfileFragment(
                    Utils(
                        requireContext()
                    ).userView
                )
            )
    }

    override fun onAuthorIconClick(slug: String) {
        Navigation.findNavController(requireView())
            .navigate(
                TagFragmentDirections.actionTagFragmentToProfileFragment(
                    Utils(
                        requireContext()
                    ).userView
                )
            )
    }

}
