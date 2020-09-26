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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.nattramn.R
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.utils.Constants
import com.example.nattramn.core.utils.snackMaker
import com.example.nattramn.databinding.FragmentProfileBinding
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.user.data.AuthLocalDataSource
import com.example.nattramn.features.user.ui.OnBottomSheetItemsClick
import com.example.nattramn.features.user.ui.OnProfileArticleListener
import com.example.nattramn.features.user.ui.UserView
import com.example.nattramn.features.user.ui.adapters.ProfileArticleAdapter
import com.example.nattramn.features.user.ui.viewmodels.ProfileViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(),
    OnProfileArticleListener, OnBottomSheetItemsClick, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileArticleAdapter: ProfileArticleAdapter
    private val dialogFragment = ActionBottomDialogFragment.newInstance(this)
    private val args: ProfileFragmentArgs by navArgs()

    private lateinit var userViewDb: UserView
    private lateinit var recyclerArticlesList: MutableList<ArticleView>

    private var currentTab = Constants.TAB_USER_ARTICLES
    private lateinit var username: String

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
        binding.swipeLayout.setOnRefreshListener(this)
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

                    currentTab = Constants.TAB_USER_ARTICLES
                    profileTab.getTabAt(0)?.customView = tabSelectedView
                    showUserArticles()

                } else {
                    currentTab = Constants.TAB_USER_FAVORITE_ARTICLES
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
            binding.swipeLayout.isRefreshing = false
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
            binding.swipeLayout.isRefreshing = false
            if (resource.status == Status.SUCCESS) {
                binding.profileArticleCount.text = resource?.data?.size.toString()
                /*(resource.data?.indices)?.forEach {
                    resource.data[it].commentsNumber =
                        profileViewModel.getUserArticlesDb(username)[it].commentsNumber
                }*/
                showRecycler(resource.data)
            } else if (resource.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    private fun showRecycler(articles: List<ArticleView>?) {
        articles?.let { recyclerArticlesList = it.toMutableList() }

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

    override fun onBookmarkClick(slug: String, isBookmarked: Boolean, position: Int, item: String) {
        if (isBookmarked) {
            profileViewModel.removeFromBookmarks(slug)
        } else {
            profileViewModel.bookmarkArticle(slug)
        }

        profileViewModel.bookmarkResult.observe(viewLifecycleOwner, Observer { result ->
            if (result.status == Status.SUCCESS) {
                recyclerArticlesList[position].bookmarked = true
                profileArticleAdapter.notifyItemChanged(position)
                snackMaker(requireView(), "این مقاله به لیست علاقه مندی ها اضافه شد")
            } else if (result.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })

        profileViewModel.removeBookmark.observe(viewLifecycleOwner, Observer { result ->
            if (result.status == Status.SUCCESS) {
                recyclerArticlesList[position].bookmarked = false
                profileArticleAdapter.notifyItemChanged(position)
                snackMaker(requireView(), "این مقاله از لیست علاقه مندی ها حذف شد")
            } else if (result.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    override fun onMoreOptionsClick(slug: String, position: Int) {
        val bundle = Bundle()
        bundle.putString("slug", slug)
        bundle.putInt("position", position)
        bundle.putString("currentTab", currentTab)
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

    override fun onArticleCommentsClick(position: Int) {}

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
                dialogFragment.dismiss()
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    override fun onDeleteArticle(slug: String, position: Int) {

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("حذف مقاله")
            .setMessage("آیا از حذف کردن مقاله مطمئن هستید؟")
            .setNegativeButton("خیر") { _, _ ->
            }
            .setPositiveButton("بله") { _, _ ->
                profileViewModel.deleteArticle(slug)
            }
            .show()

        profileViewModel.deleteArticleResult.observe(
            viewLifecycleOwner,
            Observer { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        snackMaker(requireView(), "حذف مقاله با موفقیت انجام شد")
                        profileArticleAdapter.notifyItemRemoved(position)
                        dialogFragment.dismiss()
                    }

                    Status.LOADING -> {
                        Toast.makeText(
                            requireContext(), "لطفا صبر کنید", Toast.LENGTH_SHORT
                        ).show()
                    }

                    Status.ERROR -> {
                        Toast.makeText(
                            requireContext(), "خطا در ارتباط با سرور", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    override fun onEditArticle(slug: String) {
        dialogFragment.dismiss()
        Navigation.findNavController(requireView())
            .navigate(ProfileFragmentDirections.actionProfileFragmentToWriteFragment(slug))
    }

    override fun onRefresh() {
        if (currentTab == Constants.TAB_USER_ARTICLES) {
            profileViewModel.getUserArticles(username)
        } else if (currentTab == Constants.TAB_USER_FAVORITE_ARTICLES) {
            profileViewModel.setBookmarkedArticles(username)
        }

    }

}