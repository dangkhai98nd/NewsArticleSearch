package com.example.newsarticlesearch.presenter

import android.util.Log
import com.example.newsarticlesearch.api.Client
import com.example.newsarticlesearch.api.Service
import com.example.newsarticlesearch.models.ArticleSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsPresenter(val view: INewsPresenter.View) : INewsPresenter.Presenter {
    override fun getDataNews(mapQuery: HashMap<String, String>) {
        try {

            val service: Service? = Client.getClient()?.create(Service::class.java)
            val call: Call<ArticleSearch>? = service?.getApiNews(mapQuery)
            call?.run {
                enqueue(object : Callback<ArticleSearch> {
                    override fun onFailure(call: Call<ArticleSearch>, t: Throwable) {
                        getDataNews(mapQuery)
                    }

                    override fun onResponse(call: Call<ArticleSearch>, response: Response<ArticleSearch>) {
                        view.onSuccess(response.body()?.response?.docs)
                        Log.e("done load date page ",mapQuery["page"])
                    }
                })
            }

        } catch (e: Exception) {
            Log.d("Error ", e.message)
        }
    }

}