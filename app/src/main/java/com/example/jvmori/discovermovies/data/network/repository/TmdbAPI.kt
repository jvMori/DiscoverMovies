package com.example.jvmori.discovermovies.data.network.repository

import com.example.jvmori.discovermovies.data.network.response.GenreResponse
import com.example.jvmori.discovermovies.util.Const
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TmdbAPI
{
    @GET("genre/movie/list")
    fun getGenres() : Observable<GenreResponse>

    companion object {
        val apiService : TmdbAPI = Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(TmdbAPI::class.java)
    }
}