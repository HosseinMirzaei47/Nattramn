package com.example.nattramn.features.article.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.core.resource.Resource
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.snackMaker
import com.example.nattramn.databinding.FragmentArticleBinding
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.CommentView
import com.example.nattramn.features.article.ui.OnArticleListener
import com.example.nattramn.features.article.ui.OnCommentListener
import com.example.nattramn.features.article.ui.adapters.CommentAdapter
import com.example.nattramn.features.article.ui.adapters.SuggestedArticleAdapter
import com.example.nattramn.features.article.ui.viewmodels.ArticleViewModel
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.dialog_comment.*
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(),
    OnCommentListener,
    OnArticleListener {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var suggestedArticleAdapter: SuggestedArticleAdapter
    private lateinit var dialog: Dialog
    private lateinit var articleViewArg: ArticleView
    private val args: ArticleFragmentArgs by navArgs()
    private val snapHorizontal = GravitySnapHelper(Gravity.CENTER)

    private var tags: MutableList<String>? = mutableListOf()
    private var comments: List<CommentView>? = mutableListOf()
    private lateinit var articleSlug: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        articleViewArg = args.articleView
        articleSlug = articleViewArg.slug
        tags = articleViewArg.tags?.toMutableList()
        comments = articleViewArg.commentViews

        binding = FragmentArticleBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            articleView = articleViewArg
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        snapHorizontal.attachToRecyclerView(recyclerArticleRelated)
        bookmarkArticle()
        setAddCommentAction()
        setBackButtonClick()

        articleViewArg.commentViews?.let { showCommentsRecycler(it) }

        showTagsInChipGroup()

        searchTagsAndShowSuggestionsRecycler()

        sendCommentsRequest()

        updateCurrentOrOpenSuggestion(articleSlug)

    }

    private fun showTagsInChipGroup() {
        binding.chipGroupSA.removeAllViews()

        tags?.let {
            if (it.isEmpty()) {
                binding.keywordsTextSA.visibility = View.GONE
            } else {
                for (tag in it) {
                    val chip = Chip(requireContext())
                    chip.text = tag
                    binding.chipGroupSA.addView(chip)
                }
            }
        }
    }

    private fun updateCurrentOrOpenSuggestion(slug: String) {
        articleViewModel.getSingleArticle(slug)

        articleViewModel.singleArticleResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS) {
                resource.data?.let { articleView ->

                    if (articleView.slug != articleSlug) {
                        Navigation.findNavController(requireView()).navigate(
                            ArticleFragmentDirections.actionArticleFragmentSelf(articleView)
                        )
                    } else {
                        applyArticleUpdatedData(resource, articleView)
                    }

                }
            } else if (resource.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در چک کردن آخرین تغییرات")
            }
        })
    }

    private fun searchTagsAndShowSuggestionsRecycler() {

        tags?.remove("dragons")
        tags?.remove("training")

        tags?.forEach { tag -> articleViewModel.getTagArticles(tag) }

        articleViewModel.tagArticlesResult.observe(viewLifecycleOwner, Observer { resources ->
            if (resources.status == Status.SUCCESS) {
                resources.data?.let { articles ->
                    suggestedArticleAdapter =
                        SuggestedArticleAdapter(
                            articles,
                            this
                        )

                    binding.recyclerArticleRelated.apply {
                        adapter = suggestedArticleAdapter
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    }
                }
                binding.progressSuggestions.visibility = View.GONE
            }
        })

    }

    private fun showCommentsRecycler(comments: List<CommentView>) {
        commentAdapter =
            CommentAdapter(
                comments,
                this
            )

        binding.recyclerArticleComments.apply {
            adapter = commentAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        binding.progressComments.visibility = View.GONE
        binding.commentsTitleSA.visibility = View.GONE
    }

    private fun openProfile(username: String) {
        Navigation.findNavController(requireView())
            .navigate(
                ArticleFragmentDirections.actionArticleFragmentToProfileFragment(username)
            )
    }

    private fun applyArticleUpdatedData(
        resourceArticle: Resource<ArticleView>, articleView: ArticleView
    ) {
        binding.articleView = resourceArticle.data
        articleView.commentViews?.let { showCommentsRecycler(it) }
        tags = articleView.tags?.toMutableList()
        showTagsInChipGroup()
        searchTagsAndShowSuggestionsRecycler()
    }

    private fun setAddCommentAction() {
        binding.articleCommentButton.setOnClickListener {
            dialog = Dialog(requireContext(), 0)
            dialog.apply {

                setContentView(R.layout.dialog_comment)
                show()
                window?.apply {
                    attributes?.apply {
                        width = WindowManager.LayoutParams.MATCH_PARENT
                        height = WindowManager.LayoutParams.WRAP_CONTENT
                        gravity = Gravity.TOP
                        dimAmount = 0.5f
                        y = 140
                    }
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                }
                dialogSendComment.setOnClickListener {
                    sendComment(dialogCommentText.text.toString())
                }
            }
        }
    }

    private fun bookmarkArticle() {
        binding.articleBookmark.setOnClickListener {
            articleViewModel.bookmarkArticle(articleSlug)
        }

        articleViewModel.bookmarkResult.observe(viewLifecycleOwner, Observer { result ->
            if (result.status == Status.SUCCESS) {
                snackMaker(requireView(), "این مقاله به لیست علاقه مندی ها اضافه شد")
            } else {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    private fun sendComment(comment: String) {
        articleViewModel.sendComment(articleSlug, comment)

        articleViewModel.sendCommentResult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                snackMaker(requireView(), "دیدگاه شما ثبت شد")
            } else if (it.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
            dialog.dismiss()
        })
    }

    private fun sendCommentsRequest() {
        articleViewModel.getArticleComments(articleSlug)

        articleViewModel.articleCommentsResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS && !resource.data.isNullOrEmpty()) {
                showCommentsRecycler(resource.data)
            }
        })
    }

    private fun setBackButtonClick() {
        binding.articleRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }

    /*      INTERFACES IMPLEMENTATION      */
    override fun onCardClick(slug: String) {
        updateCurrentOrOpenSuggestion(slug)
    }

    override fun onArticleTitleClick(slug: String) {
        updateCurrentOrOpenSuggestion(slug)
    }

    override fun onArticleSaveClick(slug: String) {
        articleViewModel.bookmarkArticle(slug)
    }

    override fun onAuthorNameClick(username: String) {
        openProfile(username)
    }

    override fun onAuthorIconClick(username: String) {
        openProfile(username)
    }

    override fun onCommentIconClick(username: String) {
        openProfile(username)
    }

    override fun onCommentUsernameClick(username: String) {
        openProfile(username)
    }
}