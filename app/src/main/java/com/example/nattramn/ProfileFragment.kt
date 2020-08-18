package com.example.nattramn

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nattramn.adapters.ProfileArticleAdapter
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackButtonClick()

        setRecyclers()

    }

    private fun setRecyclers() {

        val snapVertical = GravitySnapHelper(Gravity.TOP)
        snapVertical.attachToRecyclerView(recyclerProfileArticles)

        val profileArticleAdapter = ProfileArticleAdapter(requireContext())
        recyclerProfileArticles.apply {

            adapter = profileArticleAdapter
            layoutManager = LinearLayoutManager(context)

        }
    }

    private fun setBackButtonClick() {
        profileRightArrow.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

}