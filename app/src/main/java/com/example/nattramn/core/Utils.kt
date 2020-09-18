package com.example.nattramn.core

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun snackMaker(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}