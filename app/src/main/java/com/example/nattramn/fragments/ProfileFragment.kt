package com.example.nattramn.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.adapters.ProfileArticleAdapter
import com.example.nattramn.recyclerItemListeners.OnProfileArticleListener
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), OnProfileArticleListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabItemsView()

        setBackButtonClick()

        setRecyclers()

    }

    private fun setTabItemsView() {
        profileTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            @SuppressLint("InflateParams")
            override fun onTabSelected(tab: TabLayout.Tab?) {

                profileTab.getTabAt(0)?.customView = null
                profileTab.getTabAt(1)?.customView = null

                val tabSelectedView: View? =
                    LayoutInflater.from(context).inflate(R.layout.custom_tab_selected, null)

                if (tab?.position == 0 && profileTab.getTabAt(0)?.customView != tabSelectedView) {
                    profileTab.getTabAt(0)?.customView = tabSelectedView
                } else {
                    profileTab.getTabAt(1)?.customView = tabSelectedView
                }

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setRecyclers() {

        /*val snapVertical = GravitySnapHelper(Gravity.TOP)
        snapVertical.attachToRecyclerView(recyclerProfileArticles)*/

        val profileArticleAdapter = ProfileArticleAdapter(requireContext(), this)
        recyclerProfileArticles.apply {

            adapter = profileArticleAdapter
            layoutManager = LinearLayoutManager(context)

        }
    }

    private fun setBackButtonClick() {
        profileRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
            /*.navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())*/
        }
    }

    override fun onBookmarkClick(position: Int) {
        Toast.makeText(context, "Bookmark Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onMoreOptionsClick(position: Int) {
        val addPhotoBottomDialogFragment =
            ActionBottomDialogFragment.newInstance()

        addPhotoBottomDialogFragment.show(requireFragmentManager(), ActionBottomDialogFragment.TAG)
    }

    override fun onAuthorIconClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(ProfileFragmentDirections.actionProfileFragmentSelf())
    }

    override fun onAuthorNameClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(ProfileFragmentDirections.actionProfileFragmentSelf())
    }

    override fun onArticleCommentsClick(position: Int) {
        Toast.makeText(context, "Article Comments Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onArticleTitleClick(position: Int) {

        Navigation.findNavController(requireView())
            .navigate(ProfileFragmentDirections.actionProfileFragmentToArticleFragment())

    }

    override fun onArticleDescriptionClick(position: Int) {

        Navigation.findNavController(requireView())
            .navigate(ProfileFragmentDirections.actionProfileFragmentToArticleFragment())

    }

}