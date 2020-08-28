package com.example.nattramn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.Utils
import com.example.nattramn.adapters.VerticalArticleAdapter
import com.example.nattramn.databinding.FragmentTagBinding
import com.example.nattramn.recyclerItemListeners.OnArticleListener

class TagFragment : Fragment(), OnArticleListener {

    private lateinit var binding: FragmentTagBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_tag, container, false
        )
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackButtonClick()

        setRecyclers()

    }

    private fun setRecyclers() {

        val verticalAdapter = VerticalArticleAdapter(Utils(requireContext()).initArticles(), this)
        binding.recyclerTagArticles.apply {
            adapter = verticalAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setBackButtonClick() {
        binding.tagRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }

    override fun onCardClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(TagFragmentDirections.actionTagFragmentToArticleFragment())
    }

    override fun onArticleSaveClick(position: Int) {
        Toast.makeText(context, "Tag page. Article clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onAuthorNameClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                TagFragmentDirections.actionTagFragmentToProfileFragment(
                    Utils(
                        requireContext()
                    ).user
                )
            )
    }

    override fun onAuthorIconClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                TagFragmentDirections.actionTagFragmentToProfileFragment(
                    Utils(
                        requireContext()
                    ).user
                )
            )
    }

    override fun onArticleTitleClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(TagFragmentDirections.actionTagFragmentToArticleFragment())
    }

}
