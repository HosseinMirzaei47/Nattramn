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
import com.example.nattramn.adapters.HorizontalArticleAdapter
import com.example.nattramn.adapters.VerticalArticleAdapter
import com.example.nattramn.recyclerItemListeners.OnArticleListener
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), OnArticleListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnProfileClicked()

        setOnWriteClicked()

        setRecyclers()

    }

    private fun setOnWriteClicked() {

        homeWriteButton.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
        }

    }

    private fun setOnProfileClicked() {

        articleProfileIcon.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
        }

    }

    private fun setRecyclers() {

        /*Home articles vertical RecyclerView*/
        val snapVertical = GravitySnapHelper(Gravity.TOP)
        snapVertical.attachToRecyclerView(recyclerHomeArticle)

        val verticalAdapter = VerticalArticleAdapter(requireContext(), this)
        recyclerHomeArticle.apply {
            adapter = verticalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        /*Home top articles horizontal RecyclerView*/
        val snapHorizontal = GravitySnapHelper(Gravity.CENTER)
        snapHorizontal.attachToRecyclerView(recyclerHomeTopArticles)

        val horizontalAdapter = HorizontalArticleAdapter(requireContext(), this)
        recyclerHomeTopArticles.apply {
            adapter = horizontalAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onArticleSaveClick(position: Int) {
        Toast.makeText(context, getString(R.string.bookmarkArticleToast), Toast.LENGTH_SHORT).show()
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