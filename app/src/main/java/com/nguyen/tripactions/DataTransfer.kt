package com.nguyen.tripactions

import com.nguyen.tripactions.models.Article

data class ArticleContainer(val articles: List<Article>)

/*fun ArticleContainer.asDomainModel(): List<Article> {
    return articles.map {
        Article(
            section = it.section,
            subsection = it.subsection,
            title = it.title,
            abstract = it.abstract,
            webUrl = it.webUrl,
            byline = it.byline,
            multimedia = null
        )
    }
}*/

fun ArticleContainer.asDatabaseModel(): List<DbArticle> {
    return articles.map {
        DbArticle(
            section = it.section,
            subsection = it.subsection,
            title = it.title,
            abstract = it.abstract,
            webUrl = it.webUrl,
            byline = it.byline
        )
    }
}