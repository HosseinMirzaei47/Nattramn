package com.example.nattramn.features.article.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.nattramn.R
import com.example.nattramn.databinding.FragmentWriteBinding
import com.example.nattramn.features.article.ui.viewmodels.WriteViewModel
import com.hootsuite.nachos.terminator.ChipTerminatorHandler
import kotlinx.android.synthetic.main.fragment_write.*

class WriteFragment : Fragment() {

    private lateinit var binding: FragmentWriteBinding
    private lateinit var writeViewModel: WriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_write, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        writeViewModel = ViewModelProvider(this).get(WriteViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nacho_tag.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL)
        nacho_tag.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL)

        setBackButtonClick()

    }

    private fun setBackButtonClick() {

        binding.writeRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }

    }

}