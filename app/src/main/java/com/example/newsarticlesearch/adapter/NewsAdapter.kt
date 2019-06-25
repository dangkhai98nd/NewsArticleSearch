package com.example.newsarticlesearch.adapter

import android.content.Context
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.newsarticlesearch.R
import com.example.newsarticlesearch.models.News

class NewsAdapter (
    private var mContext : Context,
    private var mNewsList : List<News>
) :RecyclerView.Adapter<NewsAdapter.ItemViewHolder> ()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.item_news, p0,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = mNewsList.size ?: 0

    override fun onBindViewHolder(p0: ItemViewHolder, p1: Int) {
        val mNews = mNewsList[p1]
        Glide.with(mContext)
            .load(mNews.multimedia?.get(0)?.getUrlPath())
            .into(p0.thumbnail)

        p0.title.text = mNews.snippet

    }

    class ItemViewHolder (
        val view: View
    ): RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        var title : TextView = view.findViewById(R.id.title)

        var value = view.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val pos : Int = adapterPosition
                if (pos != RecyclerView.NO_POSITION)
                {
                    val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome"
                    val websiteURL = "http://viralandroid.com/"
                    val googleURL = "http://google.com/"

                    var mCustomTabsClient: CustomTabsClient? = null
                    var mCustomTabsSession: CustomTabsSession? = null
                    var mCustomTabsServiceConnection: CustomTabsServiceConnection? = null
                    var mCustomTabsIntent: CustomTabsIntent? = null


                }
            }
        })

    }
}