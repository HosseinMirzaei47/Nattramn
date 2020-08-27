package com.example.nattramn.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.nattramn.R
import com.example.nattramn.databinding.ActionBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActionBottomDialogFragment : BottomSheetDialogFragment() {

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

        val binding: ActionBottomSheetBinding = DataBindingUtil.inflate(
            inflater, R.layout.action_bottom_sheet, container, false
        )
        return binding.root

    }
}