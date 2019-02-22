package com.example.jvmori.discovermovies


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.jvmori.discovermovies.data.network.response.GenreResponse
import com.example.jvmori.discovermovies.ui.GenresPresenter
import com.example.jvmori.discovermovies.ui.GenresViewInterface

class MainActivity : AppCompatActivity(), GenresViewInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val genrePresenter = GenresPresenter(this)
        genrePresenter.getMovies()
    }

    override fun showProgressBar() {

    }

    override fun hideProgressBar() {

    }

    override fun displayGenres(genreResponse: GenreResponse) {
        Log.i("result", genreResponse.genres.toString())
    }

    override fun displayError(s: String) {
        Log.i("result", s)
    }
}
