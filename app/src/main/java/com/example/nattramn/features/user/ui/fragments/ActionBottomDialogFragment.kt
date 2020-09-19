package com.example.nattramn.features.user.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nattramn.core.resource.Status
import com.example.nattramn.core.snackMaker
import com.example.nattramn.databinding.ActionBottomSheetBinding
import com.example.nattramn.features.user.ui.viewmodels.ActionDialogViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActionBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: ActionBottomSheetBinding
    private lateinit var actionDialogViewModel: ActionDialogViewModel

    private lateinit var slug: String

    companion object {
        const val TAG = "ActionBottomDialog"
        fun newInstance(): ActionBottomDialogFragment {
            return ActionBottomDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actionDialogViewModel = ViewModelProvider(this).get(ActionDialogViewModel::class.java)
        slug = arguments?.getString("slug")!!

        binding = ActionBottomSheetBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onShareArticleClick()

        onDeleteArticleClick()

    }

    private fun onDeleteArticleClick() {
        binding.btnDeleteArticle.setOnClickListener {
            actionDialogViewModel.deleteArticle(slug)
        }

        actionDialogViewModel.deleteArticleResult.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    snackMaker(requireView(), "حذف مقاله با موفقیت انجام شد")
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

    private fun onShareArticleClick() {
        actionDialogViewModel.getSingleArticle(slug)

        actionDialogViewModel.singleArticleResult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                val textToShare = "${it.data?.title} \n\n${it.data?.body}"
                shareArticle(textToShare)
            } else if (it.status == Status.ERROR) {
                snackMaker(requireView(), "خطا در ارتباط با سرور")
            }
        })
    }

    private fun shareArticle(body: String?) {
        binding.btnShareArticle.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, body)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

}