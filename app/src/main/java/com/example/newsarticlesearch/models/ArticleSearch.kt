package com.example.newsarticlesearch.models



class ArticleSearch (

    var status : String? = null,
    var copyright : String? = null,
    var response: Response? = null
) {
    class Response (
        var docs : List<News>? = null,
        var meta : Meta? = null
    ) {
        class Meta (
            var hits : Int? = null,
            var offset : Int? = null,
            var time : Int? = null
        )
    }


}