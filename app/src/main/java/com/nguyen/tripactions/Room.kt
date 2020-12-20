package com.nguyen.tripactions

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {
    @Query("select * from Articles")
    fun getAll(): LiveData<List<DbArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<DbArticle>)
}

@Database(entities = [DbArticle::class], version = 1)
abstract class ArticleDatabase: RoomDatabase() {
    abstract val articleDao: ArticleDao
}

private lateinit var INSTANCE: ArticleDatabase

fun getDatabase(context: Context): ArticleDatabase {
    synchronized(ArticleDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, ArticleDatabase::class.java, "articles").build()
        }
    }
    return INSTANCE
}