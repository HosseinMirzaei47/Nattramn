package com.example.nattramn.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.adapters.VerticalArticleAdapter
import com.example.nattramn.recyclerItemListeners.OnArticleListener
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_tag.*

class TagFragment : Fragment(), OnArticleListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_tag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackButtonClick()

        setRecyclers()

    }

    private fun setRecyclers() {

        val snapVertical = GravitySnapHelper(Gravity.TOP)
        snapVertical.attachToRecyclerView(recyclerTagArticles)

        val verticalAdapter = VerticalArticleAdapter(requireContext(), this)
        recyclerTagArticles.apply {
            adapter = verticalAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setBackButtonClick() {
        articleRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(TagFragmentDirections.actionTagFragmentToHomeFragment())
        }
    }

    override fun onArticleSaveClick(position: Int) {
        Toast.makeText(context, "Tag page. Article clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onAuthorNameClick(position: Int) {
        Toast.makeText(context, getString(R.string.openProfileToast), Toast.LENGTH_SHORT).show()
    }

    override fun onAuthorIconClick(position: Int) {
        Toast.makeText(context, getString(R.string.openProfileToast), Toast.LENGTH_SHORT).show()
    }

    override fun onArticleTitleClick(position: Int) {
        Toast.makeText(context, getString(R.string.openArticleToast), Toast.LENGTH_SHORT).show()
    }

}