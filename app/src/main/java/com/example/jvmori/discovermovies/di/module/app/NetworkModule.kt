package com.example.jvmori.discovermovies.di.module.app

import com.example.jvmori.discovermovies.data.network.TmdbAPI
import com.example.jvmori.discovermovies.di.scope.ApplicationScope
import com.example.jvmori.discovermovies.util.Const
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", Const.API_KEY)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }
    }


    @Provides
    @ApplicationScope
    fun provideHttpClient(requestInterceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .build()

    @Provides
    @ApplicationScope
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Const.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @ApplicationScope
    fun provideTmdbApi(retrofit : Retrofit) = retrofit.create(TmdbAPI::class.java)
}