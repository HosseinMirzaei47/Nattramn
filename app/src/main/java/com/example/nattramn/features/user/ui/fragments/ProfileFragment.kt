package com.example.nattramn.features.user.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.utils.snackMaker
import com.example.nattramn.databinding.FragmentProfileBinding
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.AuthLocalDataSource
import com.example.nattramn.features.user.ui.OnBottomSheetItemsClick
import com.example.nattramn.features.user.ui.OnProfileArticleListener
import com.example.nattramn.features.user.ui.UserView
import com.example.nattramn.features.user.ui.adapters.ProfileArticleAdapter
import com.example.nattramn.features.user.ui.viewmodels.ProfileViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(),
    OnProfileArticleListener, OnBottomSheetItemsClick {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileArticleAdapter: ProfileArticleAdapter
    private val dialogFragment = ActionBottomDialogFragment.newInstance(this)
    private val args: ProfileFragmentArgs by navArgs()

    private lateinit var username: String
    private lateinit var userViewDb: UserView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        username = args.username
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        userViewDb = profileViewModel.getUserDb(username)

        binding = FragmentProfileBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            user = userViewDb
        }

        return binding.root
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButtonClick()
        setOnFollowButtonAction()
        setTabItemsView()

        sendProfileInfoRequest()

        showUserArticles()
    }

    private fun setTabItemsView() {
        binding.profileProgressBar.visibility = View.VISIBLE

        binding.profileTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            @SuppressLint("InflateParams")
            override fun onTabSelected(tab: TabLayout.Tab?) {

                binding.profileTab.getTabAt(0)?.customView = null
                binding.profileTab.getTabAt(1)?.customView = null

                val tabSelectedView: View? =
                    LayoutInflater.from(context).inflate(R.layout.custom_tab_selected, null)

                if (tab?.position == 0 &&
                    profileTab.getTabAt(0)?.customView != tabSelectedView
                ) {

                    profileTab.getTabAt(0)?.customView = tabSelectedView
                    showUserArticles()

                } else {
                    profileTab.getTabAt(1)?.customView = tabSelectedView
                    showBookmarkedArticles()
                }

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun showBookmarkedArticles() {
        if (username == AuthLocalDataSource().getUsername()) {
            showRecycler(profileViewModel.getBookmarkedArticlesDb())
        } else {
            showRecycler(listOf())
        }
        profileViewModel.setBookmarkedArticles(username)
        profileViewModel.profileBookmarkedArticlesResult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                showRecycler(it.data)
            } else if (it.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در جست و جوی مقالات")
            }
        })
    }

    private fun showUserArticles() {
        showRecycler(profileViewModel.getUserArticlesDb(username))
        profileViewModel.getUserArticles(username)
        profileViewModel.userArticlesResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS) {
                binding.profileArticleCount.text = resource?.data?.size.toString()
                /*(resource.data?.indices)?.forEach {
                    resource.data[it].commentsNumber =
                        profileViewModel.getUserArticlesDb(username)[it].commentsNumber
                }*/
                showRecycler(resource.data)
            } else {
                println("jalil error ${resource.message}")
            }
        })
    }

    private fun showRecycler(articles: List<ArticleView>?) {
        binding.profileArticleCount.text = articles?.size.toString()
        articles?.let { articlesList ->

            profileArticleAdapter =
                ProfileArticleAdapter(
                    articlesList,
                    this
                )
        }

        binding.recyclerProfileArticles.apply {
            adapter = profileArticleAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        binding.profileProgressBar.visibility = View.GONE
    }

    private fun sendProfileInfoRequest() {
        profileViewModel.setProfile(username)
        profileViewModel.profileResult.observe(viewLifecycleOwner, Observer { user ->
            if (user.status == Status.SUCCESS) {
                if (username == AuthLocalDataSource().getUsername()) {
                    user.data?.following = false
                }
                binding.user = user.data
            }
        })
    }

    private fun openArticle(slug: String) {
        Navigation.findNavController(requireView())
            .navigate(
                ProfileFragmentDirections.actionProfileFragmentToArticleFragment(
                    profileViewModel.getSingleArticleDb(slug)
                )
            )
    }

    private fun openProfile(username: String) {
        Navigation.findNavController(requireView())
            .navigate(
                ProfileFragmentDirections.actionProfileFragmentSelf(
                    username
                )
            )
    }

    private fun setOnFollowButtonAction() {
        if (username == AuthLocalDataSource().getUsername()) {
            binding.followButton.visibility = View.GONE
        }

        binding.followButton.setOnClickListener {
            binding.followButton.isClickable = false
            if (binding.followButton.text == "در حال دنبال کردن") {
                profileViewModel.unFollowUser(username)
            } else {
                profileViewModel.followUser(username)
            }
        }

        profileViewModel.followUserResult.observe(viewLifecycleOwner, Observer { resource ->
            binding.followButton.isClickable = true
            if (resource.status == Status.SUCCESS) {
                binding.user = resource.data
            } else if (resource.status == Status.ERROR) {
                snackMaker(requireView(), "دنبال کردن ناموفق، دوباره امتحان کنید")
            }
        })

        profileViewModel.unFollowUserResult.observe(viewLifecycleOwner, Observer { resource ->
            binding.followButton.isClickable = true
            if (resource.status == Status.SUCCESS) {
                binding.user = resource.data
            }
            snackMaker(requireView(), "درخواست ناموفق، دوباره امتحان کنید")
        })
    }

    private fun setBackButtonClick() {
        binding.profileRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun shareArticle(body: String?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, body)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        dialogFragment.dismiss()
        startActivity(shareIntent)
    }

    /*      INTERFACES IMPLEMENTATION       */
    override fun onProfileArticleCardClick(slug: String) {
        openArticle(slug)
    }

    override fun onBookmarkClick(slug: String) {
        profileViewModel.bookmarkArticle(slug)

        profileViewModel.bookmarkResult.observe(viewLifecycleOwner, Observer { result ->

            if (result.status == Status.SUCCESS) {
                snackMaker(requireView(), "این مقاله به لیست علاقه مندی ها اضافه شد")
            } else if (result.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    override fun onMoreOptionsClick(slug: String, position: Int) {
        val bundle = Bundle()
        bundle.putString("slug", slug)
        bundle.putInt("position", position)
        dialogFragment.arguments = bundle

        dialogFragment.show(childFragmentManager, ActionBottomDialogFragment.TAG)
    }

    override fun onArticleTitleClick(slug: String) {
        openArticle(slug)
    }

    override fun onArticleDescriptionClick(slug: String) {
        openArticle(slug)
    }

    override fun onAuthorNameClick(username: String) {
        openProfile(username)
    }

    override fun onAuthorIconClick(username: String) {
        openProfile(username)
    }

    override fun onArticleCommentsClick(position: Int) {
        Toast.makeText(context, "Article Comments Clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onShareArticle(slug: String) {
        profileViewModel.getSingleArticle(slug)

        profileViewModel.singleArticleResult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {

                val textToShare = "" +
                        "${it.data?.title} \n\n" +
                        "${it.data?.body} \n\n" +
                        "${it.data?.userView?.name}"

                shareArticle(textToShare)
            } else if (it.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    override fun onDeleteArticle(slug: String, position: Int) {
        profileViewModel.deleteArticle(slug)

        profileViewModel.deleteArticleResult.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    snackMaker(requireView(), "حذف مقاله با موفقیت انجام شد")
                    profileArticleAdapter.notifyItemRemoved(position)
                    dialogFragment.dismiss()
                }

                Status.LOADING -> {
                    snackMaker(requireView(), "لطفا صبر کنید")
                }

                Status.ERROR -> {
                    snackMaker(requireView(), "خطا در ارتباط با سرور")
                }
            }
        })
    }

    override fun onEditArticle(slug: String) {
        dialogFragment.dismiss()
        Navigation.findNavController(requireView())
            .navigate(ProfileFragmentDirections.actionProfileFragmentToWriteFragment(slug))
    }

}