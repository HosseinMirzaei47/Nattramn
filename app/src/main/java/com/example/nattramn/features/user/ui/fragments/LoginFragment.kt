package com.example.nattramn.features.user.ui.fragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.nattramn.R
import com.example.nattramn.core.AppDatabase
import com.example.nattramn.core.NetworkHelper
import com.example.nattramn.core.Utils
import com.example.nattramn.databinding.FragmentLoginBinding
import com.example.nattramn.features.article.data.ArticleEntity
import com.example.nattramn.features.user.data.UserEntity
import com.example.nattramn.features.user.ui.viewmodels.LoginViewModel
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private var date: Date = Date()

    companion object {
        const val minUsernameLength = 7
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSystemUI()

        Log.i("jalil", NetworkHelper.isOnline(requireContext()).toString())

        buttonOnClicks()

        /*populateDatabase()*/

    }

    private fun populateDatabase() {

        db = AppDatabase.buildDatabase(requireContext(), Utils(requireContext()).MIGRATION_1_2)
        db.articleDao().clearArticleTable()
        db.userDao().clearUserTable()

        /*Delete User On Button Click*/
        binding.loginEnterButton.setOnClickListener {
            db.userDao().deleteUser(UserEntity(1, "Hossein", "Teacher", "URL", 123, 1))
        }

        /*Initialize Database*/
        initDatabase()

        /*Update User*/
        updateUser()

        /*Search Article*/
        searchArticleByTitle("title")

        /*Users With Article Count*/
        getUsersWithArticleCount()

        /*Users With Articles And Tags And Comments*/
        usersWithAllRelatedData()

        /*Observers*/
        observeData()

    }

    private fun usersWithAllRelatedData() {
        db.userDao().getUserWithArticlesAndCommentsAndTags()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                log(it.size.toString())
            })
    }

    private fun getUsersWithArticleCount() {
        db.userDao().getUsersWithArticleCount()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                log(it[0].user.name)
                log(it[0].count.toString())
            })
    }

    private fun searchArticleByTitle(title: String) {
        db.articleDao().getArticle(title).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            log("There are ${it.size} articles with that title")
        })
    }

    private fun observeData() {
        db.userDao().getAllUsers().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            log("Observer, users size: ${it.size}")
        })
        db.articleDao().getAllArticles().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            log("Observer, articles size: ${it.size}")
        })
    }

    private fun initDatabase() {
        db.userDao().addUser(UserEntity(1, "Hossein", "Teacher", "URL", 123, 1))
        db.articleDao()
            .addArticle(ArticleEntity(1, date, "title", "body", "likes", 123, true, 1))
        db.articleDao()
            .addArticle(ArticleEntity(2, date, "title", "body", "likes", 123, true, 1))
    }

    private fun updateUser() {
        db.userDao().editUser(UserEntity(1, "Ghasem", "Teacher", "URL", 123, 1))
    }

    private fun buttonOnClicks() {

        /*binding.loginEnterButton.setOnClickListener { view ->
            if (loginUsername.text.isValidUsername()) {
                Navigation.findNavController(view)
                    .navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }

        }*/

        binding.loginRegisterButton.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

    }

    private fun CharSequence?.isValidUsername(): Boolean {

        if (this == null) {

            binding.loginUsername.requestFocus()
            binding.loginUsername.error = getString(R.string.errorEnterUsername)
            return false

        }

        if (isNullOrEmpty()) {

            binding.loginUsername.requestFocus()
            binding.loginUsername.error = getString(R.string.errorEnterUsername)
            return false

        } else if (binding.loginUsername.text!!.length > minUsernameLength
            && !Patterns.EMAIL_ADDRESS.matcher(this).matches()
        ) {

            return true

        } else if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()) {

            binding.loginUsername.requestFocus()
            binding.loginUsername.error = getString(R.string.errorEmailOrUsernameFormat)
            return false

        }

        binding.loginUsername.error = null
        return true
    }

    private fun showSystemUI() {
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

    private fun log(message: String) {
        Log.i("jalil", message)
    }

}
