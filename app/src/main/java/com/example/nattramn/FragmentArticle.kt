package com.example.nattramn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.adapters.CommentAdapter
import com.example.nattramn.adapters.SuggestedArticleAdapter
import kotlinx.android.synthetic.main.fragment_article.*

class FragmentArticle : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDialogOnCommentClick()

        setBackButtonClick()

        setRecyclers()

    }

    private fun showDialogOnCommentClick() {

        articleCommentButton.setOnClickListener {

            val commentDialogFragment = CommentDialogFragment()
            commentDialogFragment.dialog
            commentDialogFragment.show(childFragmentManager, tag)

        }

    }

    private fun setBackButtonClick() {
        articleRightArrow.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun setRecyclers() {

        val suggestedAdapter = SuggestedArticleAdapter(requireContext())
        recyclerArticleRelated.apply {
            adapter = suggestedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        val commentAdapter = CommentAdapter()
        recyclerArticleComments.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}