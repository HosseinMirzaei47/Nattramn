package com.example.nattramn.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.R
import com.example.nattramn.adapters.CommentAdapter
import com.example.nattramn.adapters.SuggestedArticleAdapter
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.dialog_comment.*
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackButtonClick()

        setAddCommentAction()

        setRecyclers()

    }

    private fun setAddCommentAction() {

        articleCommentButton.setOnClickListener {
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
        articleRightArrow.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    private fun setRecyclers() {

        /*Home articles vertical RecyclerView*/
        val snapVertical = GravitySnapHelper(Gravity.TOP)
        snapVertical.attachToRecyclerView(recyclerArticleComments)

        val commentAdapter = CommentAdapter()
        recyclerArticleComments.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        /*Home articles horizontal RecyclerView*/
        val snapHorizontal = GravitySnapHelper(Gravity.CENTER)
        snapHorizontal.attachToRecyclerView(recyclerArticleRelated)

        val suggestedAdapter = SuggestedArticleAdapter(requireContext())
        recyclerArticleRelated.apply {
            adapter = suggestedAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}