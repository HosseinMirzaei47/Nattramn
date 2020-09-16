package com.example.nattramn.features.home.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nattramn.R
import com.example.nattramn.core.Utils
import com.example.nattramn.core.ViewPagerAdapter
import com.example.nattramn.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showSystemUI()

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnProfileClicked()

        setOnWriteClicked()

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ForYouFragment(), resources.getString(R.string.HomeForYou))
        adapter.addFragment(KeyWordFragment(), resources.getString(R.string.HomeTabTitleKeywords))

        binding.viewPager.adapter = adapter
        binding.homeTabLayout.setupWithViewPager(binding.viewPager)

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
                        ).userView
                    )
                )
        }

    }

    private fun showSystemUI() {
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}