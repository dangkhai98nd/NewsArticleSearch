package com.example.newsarticlesearch.presenter

import com.example.newsarticlesearch.models.News

interface INewsPresenter {
    interface View{

        fun onSuccess(newsList: List<News>?)

    }

    interface Presenter {
        fun getDataNews(mapQuery: HashMap<String, String>)
    }
}