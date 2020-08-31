package com.example.nattramn.features.article.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.core.Utils
import com.example.nattramn.databinding.FragmentArticleBinding
import com.example.nattramn.features.article.ui.OnArticleListener
import com.example.nattramn.features.article.ui.OnCommentListener
import com.example.nattramn.features.article.ui.adapters.CommentAdapter
import com.example.nattramn.features.article.ui.adapters.SuggestedArticleAdapter
import com.example.nattramn.features.article.ui.viewmodels.ArticleViewModel
import com.example.nattramn.fragments.ArticleFragmentDirections
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.dialog_comment.*
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(),
    OnCommentListener,
    OnArticleListener {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var suggestedArticleAdapter: SuggestedArticleAdapter

    private val snapHorizontal = GravitySnapHelper(Gravity.CENTER)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_article, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        snapHorizontal.attachToRecyclerView(recyclerArticleRelated)

        setBackButtonClick()

        setAddCommentAction()

        setRecyclers()

    }

    private fun setAddCommentAction() {
        binding.articleCommentButton.setOnClickListener {
            val dialog = Dialog(requireContext(), 0)
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
                    Toast.makeText(requireContext(), dialogCommentText.text, Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }

    private fun setBackButtonClick() {
        binding.articleRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }

    private fun setRecyclers() {

        observeRecyclersContent()

        articleViewModel.setSuggestedArticles()
        articleViewModel.setComments()

        suggestedArticleAdapter =
            SuggestedArticleAdapter(
                articleViewModel.suggestedArticles.value!!,
                this
            )
        commentAdapter =
            CommentAdapter(
                articleViewModel.comments.value!!,
                this
            )

    }

    private fun observeRecyclersContent() {
        articleViewModel.suggestedArticles.observe(viewLifecycleOwner, Observer {

            suggestedArticleAdapter.suggestions = it

            binding.recyclerArticleRelated.apply {
                adapter = suggestedArticleAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

        })

        articleViewModel.comments.observe(viewLifecycleOwner, Observer {

            commentAdapter.comments = it

            binding.recyclerArticleComments.apply {
                adapter = commentAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

        })
    }

    override fun onCommentIconClick(position: Int) {
        Toast.makeText(context, getString(R.string.openProfileToast), Toast.LENGTH_SHORT).show()
    }

    override fun onCardClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(ArticleFragmentDirections.actionArticleFragmentSelf())
    }

    override fun onArticleSaveClick(position: Int) {
        Toast.makeText(context, getString(R.string.bookmarkArticleToast), Toast.LENGTH_SHORT).show()
    }

    override fun onAuthorNameClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                ArticleFragmentDirections.actionArticleFragmentToProfileFragment(
                    Utils(
                        requireContext()
                    ).user
                )
            )
    }

    override fun onAuthorIconClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                ArticleFragmentDirections.actionArticleFragmentToProfileFragment(
                    Utils(
                        requireContext()
                    ).user
                )
            )
    }

    override fun onArticleTitleClick(position: Int) {
        Navigation.findNavController(requireView())
            .navigate(ArticleFragmentDirections.actionArticleFragmentSelf())
    }

}