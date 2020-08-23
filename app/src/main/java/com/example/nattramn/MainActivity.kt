package com.example.nattramn

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    /*private var navController: NavController? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*navController = Navigation.findNavController(this, R.id.navHostFragment)*/

        hideSystemUI()

    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    /*override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController!!, null as DrawerLayout)
    }*/

}