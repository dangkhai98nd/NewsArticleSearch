package com.example.newsarticlesearch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.ComponentName
import android.net.Uri
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession
import android.view.View


class MainActivity : AppCompatActivity() {

//    val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome"
//    val websiteURL = "http://viralandroid.com/"
//    val googleURL = "http://google.com/"
//
//    var mCustomTabsClient: CustomTabsClient? = null
//    var mCustomTabsSession: CustomTabsSession? = null
//    private var mCustomTabsServiceConnection: CustomTabsServiceConnection? = null
//    private var mCustomTabsIntent: CustomTabsIntent? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mCustomTabsServiceConnection = object : CustomTabsServiceConnection() {
//            override fun onCustomTabsServiceConnected(componentName: ComponentName, customTabsClient: CustomTabsClient) {
//                mCustomTabsClient = customTabsClient
//                mCustomTabsClient!!.warmup(0L)
//                mCustomTabsSession = mCustomTabsClient!!.newSession(null)
//            }
//
//            override fun onServiceDisconnected(name: ComponentName) {
//                mCustomTabsClient = null
//            }
//        }
//
//        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection)
//
//        mCustomTabsIntent = CustomTabsIntent.Builder(mCustomTabsSession)
//            .setShowTitle(true)
//            .build()
    }

//    fun chromeCustomTabExample(view: View) {
//        mCustomTabsIntent?.launchUrl(this, Uri.parse(websiteURL))
//    }
//
//    fun chromeCustomTabGoogle(view: View) {
//        mCustomTabsIntent?.launchUrl(this, Uri.parse(googleURL))
//    }
}
