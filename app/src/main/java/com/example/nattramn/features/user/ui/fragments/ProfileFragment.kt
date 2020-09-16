package com.example.nattramn.features.user.ui.fragments

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.core.Utils
import com.example.nattramn.databinding.FragmentProfileBinding
import com.example.nattramn.features.user.ui.OnProfileArticleListener
import com.example.nattramn.features.user.ui.adapters.ProfileArticleAdapter
import com.example.nattramn.features.user.ui.viewmodels.ProfileViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(),
    OnProfileArticleListener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileArticleAdapter: ProfileArticleAdapter
    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userView = args.UserView
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_profile, container, false
        )

        binding.profile = userView
        binding.lifecycleOwner = viewLifecycleOwner
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabItemsView()

        setBackButtonClick()

        setRecyclers()

    }

    private fun setTabItemsView() {
        binding.profileTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            @SuppressLint("InflateParams")
            override fun onTabSelected(tab: TabLayout.Tab?) {

                binding.profileTab.getTabAt(0)?.customView = null
                binding.profileTab.getTabAt(1)?.customView = null

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

        setContent()
        profileViewModel.setProfileArticles()

    }

    private fun setContent() {
        binding.profileProgressBar.visibility = View.VISIBLE

        profileViewModel.profileArticles.observe(viewLifecycleOwner, Observer {

            profileArticleAdapter =
                ProfileArticleAdapter(
                    it,
                    this@ProfileFragment
                )

            binding.recyclerProfileArticles.apply {
                adapter = profileArticleAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            binding.profileProgressBar.visibility = View.INVISIBLE
            binding.profileTab.visibility = View.VISIBLE
            binding.recyclerProfileArticles.visibility = View.VISIBLE

        })

    }

    private fun setBackButtonClick() {
        binding.profileRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }

    override fun onProfileArticleCardClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                ProfileFragmentDirections.actionProfileFragmentToArticleFragment(
                    Utils(
                        requireContext()
                    ).initArticles()[0]
                )
            )
    }

    override fun onBookmarkClick(position: Int) {
        Toast.makeText(context, "Bookmark Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onMoreOptionsClick(position: Int) {
        val addPhotoBottomDialogFragment =
            ActionBottomDialogFragment.newInstance()

        @Suppress("DEPRECATION")
        addPhotoBottomDialogFragment.show(
            requireFragmentManager(),
            ActionBottomDialogFragment.TAG
        )
    }

    override fun onAuthorIconClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                ProfileFragmentDirections.actionProfileFragmentSelf(
                    Utils(
                        requireContext()
                    ).userView
                )
            )
    }

    override fun onAuthorNameClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                ProfileFragmentDirections.actionProfileFragmentSelf(
                    Utils(
                        requireContext()
                    ).userView
                )
            )
    }

    override fun onArticleCommentsClick(position: Int) {
        Toast.makeText(context, "Article Comments Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onArticleTitleClick(position: Int) {

        Navigation.findNavController(requireView())
            .navigate(
                ProfileFragmentDirections.actionProfileFragmentToArticleFragment(
                    Utils(
                        requireContext()
                    ).initArticles()[0]
                )
            )

    }

    override fun onArticleDescriptionClick(position: Int) {

        Navigation.findNavController(requireView())
            .navigate(
                ProfileFragmentDirections.actionProfileFragmentToArticleFragment(
                    Utils(
                        requireContext()
                    ).initArticles()[0]
                )
            )

    }

}