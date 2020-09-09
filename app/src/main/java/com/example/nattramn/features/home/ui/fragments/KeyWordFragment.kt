package com.example.nattramn.features.home.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nattramn.R
import com.example.nattramn.databinding.FragmentKeyWordBinding

class KeyWordFragment : Fragment() {

    private lateinit var binding: FragmentKeyWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_key_word, container, false)
        return binding.root
    }

}