package com.example.nattramn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonOnClicks()

    }

    private fun buttonOnClicks() {

        loginEnterButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        loginRegisterButton.setOnClickListener {

            val registerFragment = RegisterFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.loginFrame, registerFragment)
                .addToBackStack(null)
                .commit()

        }
    }
}