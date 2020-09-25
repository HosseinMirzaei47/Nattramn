package com.example.nattramn.features.home.data

import com.example.nattramn.core.config.MyApp
import com.example.nattramn.core.database.AppDatabase

class HomeLocalDataSource {

    private val db = AppDatabase.buildDatabase(MyApp.app)

    fun getAllTagsOperation() {

    }

}