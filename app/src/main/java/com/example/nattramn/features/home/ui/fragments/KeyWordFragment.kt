package com.example.nattramn.features.home.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.utils.snackMaker
import com.example.nattramn.databinding.FragmentKeyWordsBinding
import com.example.nattramn.features.home.ui.OnTagsItemListener
import com.example.nattramn.features.home.ui.TagAdapter
import com.example.nattramn.features.home.ui.viewmodels.KeyWordsViewModel

class KeyWordFragment : Fragment(), OnTagsItemListener {

    private lateinit var binding: FragmentKeyWordsBinding
    private lateinit var keyWordsViewModel: KeyWordsViewModel
    private lateinit var tagAdapter: TagAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        keyWordsViewModel = ViewModelProvider(this).get(KeyWordsViewModel::class.java)

        binding = FragmentKeyWordsBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAllTagsRecycler(keyWordsViewModel.getAllTagsDb())
        sendAllTagsRequest()
        observeAllTagsResponse()

    }

    private fun observeAllTagsResponse() {
        keyWordsViewModel.allTagsResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS) {
                resource.data?.tags?.let { tags ->
                    showAllTagsRecycler(tags)
                }
            } else if (resource.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
                binding.progressAllTags.visibility = View.GONE
            }
        })
    }

    private fun sendAllTagsRequest() {
        keyWordsViewModel.getAllTags()
    }

    private fun showAllTagsRecycler(tags: List<String>) {
        tagAdapter = TagAdapter(
            tags,
            this
        )
        binding.recyclerAllTags.apply {
            adapter = tagAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
        binding.progressAllTags.visibility = View.GONE
    }

    override fun onTagClick(tag: String) {
        Navigation.findNavController(requireView())
            .navigate(HomeFragmentDirections.actionHomeFragmentToTagFragment(tag))
    }

}