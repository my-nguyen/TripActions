package com.nguyen.tripactions.models

import java.io.Serializable

class Article(val section: String, val subsection: String?, val title: String, val abstract: String,
              val webUrl: String, val byline: String?, multimedia: List<MultimediaAS>?) : Serializable {

    constructor(result: Result) : this(result.section, result.subsection, result.title, result.abstract, result.url, result.byline, null)

    constructor(doc: Doc) : this(doc.section_name, doc.subsection_name, doc.headline.main, doc.abstract, doc.web_url, doc.byline.original, doc.multimedia)

    var imageUrl: String? = null

    init {
        if (multimedia != null) {
            for (media in multimedia) {
                if (media.subtype == "thumbnail") {
                    imageUrl = "http://www.nytimes.com/${media.url}"
                    break
                }
            }
        }
    }

    override fun toString(): String {
        return "section: $section, subsection: $subsection\ntitle: $title\nabstract: $abstract\nbyline: $byline"
    }
}
