package com.example.jvmori.discovermovies.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.jvmori.discovermovies.data.local.entity.Genre
import com.example.jvmori.discovermovies.data.local.entity.MovieResult

const val genreIdKey = "genreId"
const val movieResultKey = "movieResult"

fun navigateToDetails(item : MovieResult, fragment : Fragment, actionId : Int){
    val bundle = Bundle()
    bundle.putSerializable(movieResultKey, item)
    NavHostFragment.findNavController(fragment).navigate(actionId, bundle)
}

fun navigateToMovieList(item : Genre, fragment: Fragment, actionId: Int){
    val bundle = Bundle()
    bundle.putInt(genreIdKey, item.idGenre)
    NavHostFragment.findNavController(fragment).navigate(actionId, bundle)
}
