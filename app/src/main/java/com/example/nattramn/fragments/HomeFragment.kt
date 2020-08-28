package com.example.nattramn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nattramn.R
import com.example.nattramn.Utils
import com.example.nattramn.adapters.ViewPagerAdapter
import com.example.nattramn.databinding.FragmentHomeBinding
import com.example.nattramn.fragments.pager.ForYouFragment
import com.example.nattramn.fragments.pager.KeyWordFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() /*, OnArticleListener */ {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnProfileClicked()

        setOnWriteClicked()

        // setRecyclers()

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(ForYouFragment(), resources.getString(R.string.HomeForYou))
        adapter.addFragment(KeyWordFragment(), resources.getString(R.string.HomeTabTitleKeywords))
        binding.viewPager.adapter = adapter
        binding.homeTabLayout.setupWithViewPager(viewPager)

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
                        ).user
                    )
                )
        }

    }

    /* private fun setRecyclers() {

         val verticalAdapter = VerticalArticleAdapter(Utils(requireContext()).initArticles(), this)
         binding.recyclerHomeArticle.apply {
             adapter = verticalAdapter
             layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
         }

         val snapHorizontal = GravitySnapHelper(Gravity.CENTER)
         snapHorizontal.attachToRecyclerView(recyclerHomeTopArticles)

         val horizontalAdapter =
             HorizontalArticleAdapter(Utils(requireContext()).initArticles(), this)
         binding.recyclerHomeTopArticles.apply {
             adapter = horizontalAdapter
             layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
         }
     }

     override fun onCardClick(position: Int) {
         Navigation.findNavController(requireView())
             .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
     }

     override fun onArticleSaveClick(position: Int) {
         Navigation.findNavController(requireView())
             .navigate(HomeFragmentDirections.actionHomeFragmentToTagFragment())
     }

     override fun onAuthorNameClick(position: Int) {
         Navigation.findNavController(requireView())
             .navigate(
                 HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                     Utils(
                         requireContext()
                     ).user
                 )
             )
     }

     override fun onAuthorIconClick(position: Int) {
         Navigation.findNavController(requireView())
             .navigate(
                 HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                     Utils(
                         requireContext()
                     ).user
                 )
             )
     }

     override fun onArticleTitleClick(position: Int) {
         Navigation.findNavController(requireView())
             .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
     } */

}