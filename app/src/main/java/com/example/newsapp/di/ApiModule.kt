package com.example.newsapp.di

import com.example.data.network.ApiService
import com.example.data.network.model.ContentApi
import com.example.data.network.model.ContentApiMapper
import com.example.domain.Content
import com.example.domain.utils.DomainMapper
import com.example.newsapp.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    private val url = "https://content.guardianapis.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val httpUrl = original.url()
            val newHttpUrl =
                httpUrl.newBuilder().addQueryParameter("api-key", BuildConfig.GUARDIAN_API_KEY).build()
            println("WOW")
            println(newHttpUrl)
            val requestBuilder = original.newBuilder().url(newHttpUrl)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
    }

    @Singleton
    @Provides
    fun providesAPI(retrofit: Retrofit.Builder): ApiService {
        return retrofit.build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesContentApiMapper(contentApiMapper: ContentApiMapper): DomainMapper<ContentApi, Content> {
        return contentApiMapper
    }
}