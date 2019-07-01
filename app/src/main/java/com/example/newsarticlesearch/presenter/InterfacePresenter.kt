package com.example.newsarticlesearch.presenter

import com.example.newsarticlesearch.models.News

interface InterfacePresenter {
    interface View{

        fun onSuccess(newsList: List<News>?)

    }

    interface Presenter {
        fun getDataNews(mapQuery: HashMap<String, String>)
    }
}