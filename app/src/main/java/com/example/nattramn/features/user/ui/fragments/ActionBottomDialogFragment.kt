package com.example.nattramn.features.user.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
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

        /** below code contains a bug **/
        /*binding.btnDeleteArticle.setOnClickListener {
            actionDialogViewModel.deleteArticle(slug)
        }

        actionDialogViewModel.deleteArticleResult.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT).show()
                }

                Status.LOADING -> {
                    Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }
        })*/

    }

}