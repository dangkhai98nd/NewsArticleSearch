package com.example.newsarticlesearch.api



import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
   companion object {
       var BASE_URL : String = "https://api.nytimes.com/svc/search/v2/"
       var retrofit : Retrofit? = null
       fun getClient () : Retrofit? {
           if (retrofit == null)
           {
               retrofit = Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()
           }
           return retrofit
       }
   }
}