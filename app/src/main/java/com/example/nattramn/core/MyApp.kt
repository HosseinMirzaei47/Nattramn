package com.example.nattramn.core

import android.app.Application
import com.example.nattramn.core.storage.data.PreferenceProperty.Companion.APP_PREF_NAME
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.BuildConfig
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader


class MyApp : Application() {
    companion object {
        val networkFlipperPlugin = NetworkFlipperPlugin()
        lateinit var app: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        SoLoader.init(this, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.addPlugin(networkFlipperPlugin)
            client.addPlugin(SharedPreferencesFlipperPlugin(this, APP_PREF_NAME))
            client.start()
        }
    }
}
