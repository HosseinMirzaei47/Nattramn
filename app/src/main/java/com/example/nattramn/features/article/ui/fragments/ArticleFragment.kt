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
import com.example.nattramn.core.Utils
import com.example.nattramn.core.resource.Status
import com.example.nattramn.databinding.FragmentArticleBinding
import com.example.nattramn.features.article.ui.ArticleView
import com.example.nattramn.features.article.ui.OnArticleListener
import com.example.nattramn.features.article.ui.OnCommentListener
import com.example.nattramn.features.article.ui.adapters.CommentAdapter
import com.example.nattramn.features.article.ui.adapters.SuggestedArticleAdapter
import com.example.nattramn.features.article.ui.viewmodels.ArticleViewModel
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var tags: List<String>

    private val snapHorizontal = GravitySnapHelper(Gravity.CENTER)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        articleViewArg = args.ArticleView
        tags = articleViewArg.tags

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

        setBackButtonClick()

        onBookmarkClick()

        setAddCommentAction()

        setArticleTags()

        setRecyclers()

        setComments()

    }

    private fun setArticleTags() {

        if (tags.isEmpty()) {
            binding.keywordsTextSA.visibility = View.GONE
        } else {
            for (tag in tags) {
                val chip = Chip(requireContext())
                chip.text = tag
                binding.chipGroupSA.addView(chip)
            }
        }

    }

    private fun onBookmarkClick() {
        binding.articleBookmark.setOnClickListener {
            articleViewModel.bookmarkArticle(args.ArticleView.slug)
        }

        articleViewModel.bookmarkResult.observe(viewLifecycleOwner, Observer { result ->
            if (result.status == Status.SUCCESS) {
                Snackbar.make(
                    requireView(), "این مقاله به لیست علاقه مندی ها اضافه شد", Snackbar.LENGTH_LONG
                ).show()
            } else {
                Snackbar.make(
                    requireView(), "خطا در ارتباط با سرور", Snackbar.LENGTH_LONG
                ).show()
            }
        })
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

    private fun sendComment(comment: String) {
        articleViewModel.sendComment(args.ArticleView.slug, comment)

        articleViewModel.sendCommentResult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                Snackbar.make(requireView(), "دیدگاه شما ثبت شد", Snackbar.LENGTH_SHORT).show()
            } else if (it.status == Status.ERROR) {
                Snackbar.make(requireView(), "خطا در ارتباط با سرور", Snackbar.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        })
    }

    private fun setBackButtonClick() {
        binding.articleRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun setComments() {
        articleViewModel.getArticleComments(args.ArticleView.slug)

        articleViewModel.articleCommentsResult.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS) {

                if (resource.data?.comments?.isEmpty()!!) {
                    binding.commentsTitleSA.visibility = View.GONE
                } else {
                    resource.data.let { article ->
                        commentAdapter =
                            CommentAdapter(
                                article.comments,
                                this
                            )
                    }

                    binding.recyclerArticleComments.apply {
                        adapter = commentAdapter
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                }

            }
        })
    }

    private fun setRecyclers() {

        tags.forEach { articleViewModel.getTagArticles(it) }

        articleViewModel.tagArticlesResult.observe(viewLifecycleOwner, Observer { resources ->
            if (resources.status == Status.SUCCESS) {
                resources.data?.let {
                    suggestedArticleAdapter =
                        SuggestedArticleAdapter(
                            resources.data,
                            this
                        )

                    binding.recyclerArticleRelated.apply {
                        adapter = suggestedArticleAdapter
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    }
                }
            }
        })

    }

    private fun openProfile(username: String) {
        Navigation.findNavController(requireView())
            .navigate(
                ArticleFragmentDirections.actionArticleFragmentToProfileFragment(username)
            )
    }

    override fun onCardClick(slug: String) {
        Navigation.findNavController(requireView())
            .navigate(ArticleFragmentDirections.actionArticleFragmentSelf(Utils(requireContext()).initArticles()[0]))
    }

    override fun onArticleTitleClick(slug: String) {
        Navigation.findNavController(requireView())
            .navigate(ArticleFragmentDirections.actionArticleFragmentSelf(Utils(requireContext()).initArticles()[0]))
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