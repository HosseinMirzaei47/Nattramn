package com.example.nattramn.features.article.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.nattramn.databinding.FragmentWriteBinding
import com.example.nattramn.features.article.ui.viewmodels.WriteViewModel
import com.google.android.material.chip.Chip

class WriteFragment : Fragment() {

    private lateinit var binding: FragmentWriteBinding
    private lateinit var writeViewModel: WriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        writeViewModel = ViewModelProvider(this).get(WriteViewModel::class.java)

        binding = FragmentWriteBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackButtonClick()

        binding.tagsEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.last() == ' ') {
                    val chip = Chip(requireContext())
                    chip.text = s.subSequence(0, s.lastIndex - 1)
                    binding.writeChipGroup.addView(chip, 0)
                }
            }

        })

    }

    private fun setBackButtonClick() {
        binding.writeRightArrow.setOnClickListener { view ->
            Navigation.findNavController(view).navigateUp()
        }
    }

}