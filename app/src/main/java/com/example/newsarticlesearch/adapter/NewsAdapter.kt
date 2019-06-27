package com.example.newsarticlesearch.adapter

import android.content.ComponentName
import android.content.Context
import android.net.Uri
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
    private var mCustomTabsIntent: CustomTabsIntent?
) :RecyclerView.Adapter<NewsAdapter.ItemViewHolder> ()
{

    private var mNewsList : MutableList<News> = arrayListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.item_news, p0,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = mNewsList.size

    override fun onBindViewHolder(p0: ItemViewHolder, p1: Int) {
        val mNews = mNewsList[p1]
        val multimediaList : List<News.Multimedia>? = mNews.multimedia
        if (multimediaList?.size != 0)
        {
            p0.thumbnail.visibility = ImageView.VISIBLE
            Glide.with(mContext)
                .load(mNews.multimedia?.get(0)?.getUrlPath())
                .thumbnail(Glide.with(mContext).load(R.drawable.icon_load))
                .fitCenter()
                .into(p0.thumbnail)
        }
        else {
            p0.thumbnail.visibility = ImageView.GONE
        }

        p0.title.text = mNews.snippet


    }

    inner class ItemViewHolder (
        val view: View
    ): RecyclerView.ViewHolder(view) {
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
        var title : TextView = view.findViewById(R.id.title)

        var value = view.setOnClickListener {
            val pos : Int = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                mCustomTabsIntent?.launchUrl(mContext, Uri.parse(mNewsList[pos].web_url))

            }
        }

    }

    fun addAll( morenews: List<News>) {
        mNewsList.addAll(morenews)
        notifyDataSetChanged()
    }

    fun clearList()
    {
        mNewsList = arrayListOf()
    }

}