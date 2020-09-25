package com.example.nattramn.features.user.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nattramn.databinding.ActionBottomSheetBinding
import com.example.nattramn.features.user.ui.OnBottomSheetItemsClick
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActionBottomDialogFragment(val listener: OnBottomSheetItemsClick) :
    BottomSheetDialogFragment() {

    private lateinit var binding: ActionBottomSheetBinding

    private lateinit var slug: String
    private var position = 0

    companion object {
        const val TAG = "ActionBottomDialog"
        fun newInstance(listener: OnBottomSheetItemsClick): ActionBottomDialogFragment {
            return ActionBottomDialogFragment(listener)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slug = arguments?.getString("slug")!!
        position = arguments?.getInt("position")!!

        binding = ActionBottomSheetBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onEditArticleClick()

        onDeleteArticleCllck()

        onShareArticleClick()

    }

    private fun onShareArticleClick() {
        binding.shareArticleButton.setOnClickListener {
            listener.onShareArticle(slug)
        }
    }

    private fun onDeleteArticleCllck() {
        binding.deleteArticleButton.setOnClickListener {
            listener.onDeleteArticle(slug, position)
        }
    }

    private fun onEditArticleClick() {
        binding.editArticleButton.setOnClickListener {
            listener.onEditArticle(slug)
        }
    }

}