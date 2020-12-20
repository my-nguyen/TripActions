package com.nguyen.tripactions.dependencies

import com.nguyen.tripactions.models.NYTimesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    companion object {
        const val BASE_URL = "https://api.nytimes.com/svc/"
    }

    @Provides
    fun provideGsonConverterFactory() : GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(factory: GsonConverterFactory) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    fun provideArticleAPI(retrofit: Retrofit) : NYTimesService {
        return retrofit.create(NYTimesService::class.java)
    }
}