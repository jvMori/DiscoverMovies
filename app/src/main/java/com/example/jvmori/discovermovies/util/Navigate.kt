package com.example.jvmori.discovermovies.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.jvmori.discovermovies.data.local.entity.MovieResult

fun navigateToDetails(item : MovieResult, fragment : Fragment, actionId : Int){
    val bundle = Bundle()
    bundle.putSerializable("movieResult", item)
    NavHostFragment.findNavController(fragment).navigate(actionId, bundle)
}
