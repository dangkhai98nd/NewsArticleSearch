package com.example.newsarticlesearch.models



class News (
    var web_url : String? = null,
    var snippet : String? = null,
    var lead_paragraph : String? = null,
    var abstract : String? = null,
    var blog : List<String>? = null,
    var source : String? = null,
    var multimedia : List<Multimedia>? = null,
    var headline : Headline? = null,
    var keywords : List<Keyword>? = null,
    var pub_date : String? = null,
    var document_type : String? = null,
    var news_desk: String? = null,
    var section_name : String? = null,
    var byline : Byline? = null,
    var type_of_material : String? = null,
    var _id : String? = null,
    var word_count : Int? = null,
    var uri : String? = null
) {

    class Byline (
        var original : String? = null,
        var person : List<Person>? = null,
        var organization : String? = null
    ) {

        class Person (
            var firstname : String? = null,
            var middlename : String? = null,
            var lastname : String? = null,
            var qualifier : String? = null,
            var title : String? = null,
            var role : String? = null,
            var organization: String? = null,
            var rank : Int? = null
        )
    }

    class Keyword (
        var name : String? = null,
        var value : String? = null,
        var rank : Int? = null,
        var major : String? = null
    )

    class Headline(
        var main : String? = null,
        var kicker : String? = null,
        var content_kicker: String? = null,
        var print_headline : String? =null,
        var name : String? = null,
        var seo : String? = null,
        var sub : String? = null
    )


    class Multimedia (
        var rank : Int? = null,
        var subtype : String? = null,
        var caption : String? = null,
        var credit : String? = null,
        var type : String? = null,
        var url : String? = null,
        var height : Int? = null,
        var width : Int? = null,
        var legacy : Legacy? = null,
        var subType : String? = null,
        var crop_name: String? = null
    ) {

        class Legacy (
            var widewidth : Int? = null,
            var wideheight : Int? =null,
            var wide : String? = null
        ) {

            fun getWidePath () : String = "https://www.nytimes.com/$wide"
        }

        fun getUrlPath (): String = "https://www.nytimes.com/$url"
    }

}