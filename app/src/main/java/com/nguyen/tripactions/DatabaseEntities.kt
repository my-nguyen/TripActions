package com.nguyen.tripactions

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nguyen.tripactions.models.Article

@Entity(tableName="Articles")
data class DbArticle constructor(
    @PrimaryKey
    var webUrl: String = "",
    var section: String = "",
    var subsection: String? = "",
    var title: String = "",
    var abstract: String = "",
    var byline: String? = "",
    var imageUrl: String? = ""
)

fun List<DbArticle>.asDomainModel(): List<Article> {
    return map {
        Article(
            webUrl = it.webUrl,
            section = it.section,
            subsection = it.subsection,
            title = it.title,
            abstract = it.abstract,
            byline = it.byline,
            multimedia = null)
    }
}